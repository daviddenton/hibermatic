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
public abstract class AbstractSearchFilter<T extends AbstractSearchFilter, B> implements Serializable {
    private final Class<? extends B> entityClass;
    private final Map<Object, FieldFilter> filtersMap = new HashMap<Object, FieldFilter>();

    protected AbstractSearchFilter(Class<? extends B> entityClass) {
        this.entityClass = entityClass;
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
     * Searches using the current set of defined filters and returns the results.
     *
     * @param session
     * @return List of results of type defined by the entity class.
     */
    public List<B> search(Session session) {
        return getExecutor().search(toCriteria().getExecutableCriteria(session));
    }

    protected abstract SearchExecutor getExecutor();
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
    private DetachedCriteria toCriteria() {
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
    private DetachedCriteria toCountCriteria() {
        return toCriteria().setProjection(Projections.rowCount());
    }

}
