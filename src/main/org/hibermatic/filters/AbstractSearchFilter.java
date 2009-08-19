package org.hibermatic.filters;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSearchFilter<T extends AbstractSearchFilter, B> implements Serializable, SearchFilter<B> {
    private static final int UNLIMITED_PAGE_SIZE = -1;
    private final Class entityClass;
    private final Map<String, FieldFilter> filtersMap;

    private int pageSize;
    private int firstRow;

    protected AbstractSearchFilter(int pageSize, Class entityClass) {
        this.entityClass = entityClass;
        this.firstRow = 0;
        this.pageSize = pageSize;
        this.filtersMap = new HashMap<String, FieldFilter>();
    }

    protected AbstractSearchFilter(Class entityClass) {
        this(UNLIMITED_PAGE_SIZE, entityClass);
    }

    public void goToRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T addFilter(String associationPath, FieldFilter fieldFilter) {
        filtersMap.put(associationPath, fieldFilter);
        return (T) this;
    }

    public boolean containsFilterFor(String fieldFilterName) {
        return filtersMap.containsKey(fieldFilterName);
    }
    
    protected T eqFilter(String associationPath, Object object) {
        if (object != null) {
            addFilter(associationPath, new EntityFieldFilter(associationPath).eq(object));
        }
        return (T) this;
    }

    protected T neFilter(String associationPath, Object object) {
        if (object != null) {
            addFilter(associationPath, new EntityFieldFilter(associationPath).ne(object));
        }
        return (T) this;
    }

    public DetachedCriteria toCriteria() {
        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        for (FieldFilter filter : new ArrayList<FieldFilter>(filtersMap.values())) {
            filter.applyTo(criteria);
        }

        return criteria;
    }

    public DetachedCriteria toCountCriteria() {
        return toCriteria().setProjection(Projections.rowCount());
    }
}
