package org.hibermatic.filters;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows several FieldFilters to be grouped and used as a pre-defined search unit. To use:<br/><br/>
 * <p/>
 * 1. construct the filter<br/>
 * 2. add individual FieldFilters<br/>
 * 3. set paging information<br/>
 * 4. use to create criteria to pass to hibernate for both result set and total match counts<br/>
 *
 * @param <T> The class of the subclass
 * @param <B> The class of the searchable entity
 */
public abstract class AbstractSearchFilter<T extends AbstractSearchFilter, B> implements Serializable, SearchFilter<B> {
    private static final int UNLIMITED_PAGE_SIZE = -1;
    private final Class entityClass;
    private final Map<Object, FieldFilter> filtersMap;

    private int pageSize;
    private int firstRow;

    /**
     * A filter with the specified page size for the searchable entity
     *
     * @param pageSize
     * @param entityClass
     */
    protected AbstractSearchFilter(int pageSize, Class entityClass) {
        this.entityClass = entityClass;
        this.firstRow = 0;
        this.pageSize = pageSize;
        this.filtersMap = new HashMap<Object, FieldFilter>();
    }

    /**
     * A filter with unlimited page size for the searchable entity
     *
     * @param entityClass
     */
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

    /**
     * @return the first row number of the resultset to return
     */
    public int getFirstRow() {
        return firstRow;
    }

    /**
     * @return the configured page size. -1 for unlimited page size
     */
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

    /**
     * Creates a an DetachedCriteria for the desired entity class with all criterion's for the individual FieldFilters applied, but also adds
     * a projection to count the total number of results (irrespective of page size). This is especially useful for determining the number of pages
     * in a particular result set.
     *
     * @return Modified DetachedCriteria object with the results set to be a total row count of all matching entities.
     */
    public DetachedCriteria toCountCriteria() {
        return toCriteria().setProjection(Projections.rowCount());
    }
}
