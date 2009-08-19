package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;

/**
 * General interface for all Search Filter implementations.
 * @param <B> The class of the searchable entity
 */
public interface SearchFilter<B> {

    DetachedCriteria toCriteria();

    DetachedCriteria toCountCriteria();

    void setPageSize(int pageSize);

    void goToRow(int firstRow);

    int getFirstRow();

    int getPageSize();
}
