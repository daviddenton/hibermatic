package org.hibermatic.filters;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Allows several FieldFilters to be grouped and used as a pre-defined search unit. To use:<br/><br/>
 * <p/>
 * 1. construct the filter<br/>
 * 2. add individual FieldFilters<br/>
 * 3. set paging information<br/>
 * 4. call the search or paging methods to navigate the result set<br/>
 *
 * @param <T> The class of the subclass
 * @param <B> The class of the searchable entity
 */
public abstract class AbstractSearchFilter<T extends AbstractSearchFilter, B> implements Serializable, SearchFilter<B> {
    private static final int UNLIMITED_PAGE_SIZE = -1;
    private final Class<? extends B> entityClass;
    private final Map<Object, FieldFilter> filtersMap;

    private int pageSize;
    private int firstRow;

    /**
     * A filter with the specified page size for the searchable entity
     *
     * @param pageSize
     * @param entityClass
     */
    protected AbstractSearchFilter(int pageSize, Class<? extends B> entityClass) {
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
     * Sets the first returned row of the resultset. Zero indexed, which is also the minimum.
     *
     * @param newFirstRow
     */
    public T goToRow(int newFirstRow) {
        this.firstRow = newFirstRow < 0 ? 0 : newFirstRow;
        return (T) this;
    }

    /**
     * @return the first row number of the resultset to return. Zero indexed.
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
     * The maximum number of results returned in the dataset.
     *
     * @param pageSize
     */
    public SearchFilter<B> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
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
     * @return is the filter already exists
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
     * Returns the nth page of results using the current set of defined filters. Page numbers start at 1.
     *
     * @param pageNumber
     * @throws IllegalArgumentException if page size is unlimited
     * @return This filter
     */
    public T goToPage(int pageNumber) throws IllegalArgumentException {
        ensurePagingEnabled();
        if(pageNumber < 1) pageNumber = 1;
        goToRow(pageSize * pageNumber);
        return (T) this;
    }

    private void ensurePagingEnabled() {
        if(pageSize == UNLIMITED_PAGE_SIZE)
            throw new IllegalArgumentException("Can't use paging with unlimited page size");
    }

    /**
     * Returns the next page of results using the current set of defined filters.
     *
     * @throws IllegalArgumentException if page size is unlimited
     * @return This filter
     */
    public T goToNextPage() {
        ensurePagingEnabled();
        return goToRow(firstRow + pageSize);
    }

    /**
     * Returns the previous page of results using the current set of defined filters. If already on the first page,
     * this has no effect.
     *
     * @throws IllegalArgumentException if page size is unlimited
     * @return This filter
     */
    public T goToPreviousPage() {
        ensurePagingEnabled();
        return goToRow(firstRow - pageSize);
    }

    /**
     * Returns the current page of results using the current set of defined filters.
     *
     * @param session
     * @throws IllegalArgumentException if page size is unlimited
     * @return Page of results of type defined by the entity class.
     */
    public PagedSearchResults<B> currentPage(Session session) {
        ensurePagingEnabled();
        return new PagedSearchResults<B>(firstRow, pageSize, search(session), count(session));
    }

    /**
     * Searches using the current set of defined filters and returns the results.
     *
     * @param session
     * @return List of results of type defined by the entity class.
     */
    public List<B> search(Session session) {
        return toCriteria().getExecutableCriteria(session).setFirstResult(firstRow).setMaxResults(pageSize).list();
    }

    /**
     * Irrespective of the set page size, count the number of matches using the current set of defined filters;
     *
     * @param session
     * @return The total number of matches
     */
    public int count(Session session) {
        return (Integer) toCountCriteria().getExecutableCriteria(session).uniqueResult();
    }

    /**
     * Creates a DetachedCriteria for the desired entity class with all criterion's for the individual FieldFilters applied.
     *
     * @return Modified DetachedCriteria object
     * @deprecated use the paging or searching methods instead. To be removed in v2
     */
    @Deprecated
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
     * @deprecated use the paging or searching methods instead. To be removed in v2
     */
    @Deprecated
    public DetachedCriteria toCountCriteria() {
        return toCriteria().setProjection(Projections.rowCount());
    }

}
