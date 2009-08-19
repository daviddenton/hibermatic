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
    private final Map<Object, FieldFilter> filtersMap;

    private int pageSize;
    private int firstRow;

    protected AbstractSearchFilter(int pageSize, Class entityClass) {
        this.entityClass = entityClass;
        this.firstRow = 0;
        this.pageSize = pageSize;
        this.filtersMap = new HashMap<Object, FieldFilter>();
    }

    protected AbstractSearchFilter(Class entityClass) {
        this(UNLIMITED_PAGE_SIZE, entityClass);
    }

    /**
     * Sets the first returned row of the resultset.
     *
     * @param firstRow
     */
    public void goToRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * The maximum number of results returned in this dataset.
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Adds a field filter keyed against the particular association name. Subsequent calls with the same key will overwrite the used filter (eg. for sorting)
     *
     * @param key
     * @param fieldFilter
     * @return This filter
     */
    public T addFilter(Object key, FieldFilter fieldFilter) {
        filtersMap.put(key, fieldFilter);
        return (T) this;
    }

    /**
     * Checks if a field filter already exists for this key
     *
     * @param key
     * @return
     */
    public boolean containsFilterFor(Object key) {
        return filtersMap.containsKey(key);
    }

    /**
     * Convienience method for adding an "equals" EntityFilter
     *
     * @param associationPath
     * @param object
     * @return This filter
     */
    protected T eqFilter(String associationPath, Object object) {
        if (object != null) {
            addFilter(associationPath, new EntityFieldFilter(associationPath).eq(object));
        }
        return (T) this;
    }

    /**
     * Convienience method for adding an "not equal" EntityFilter
     *
     * @param associationPath
     * @param object
     * @return This filter
     */
    protected T neFilter(String associationPath, Object object) {
        if (object != null) {
            addFilter(associationPath, new EntityFieldFilter(associationPath).ne(object));
        }
        return (T) this;
    }

    /**
     * Creates a DetachedCriteria for the desired entity class with all criterion's for the individual FieldFilters applied.
     *
     * @return Modified DetachedCriteria object
     */
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
