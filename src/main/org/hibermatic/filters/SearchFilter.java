package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;

/**
 * General interface for all Search Filter implementations.
 */
public interface SearchFilter {

    DetachedCriteria toCriteria();

    DetachedCriteria toCountCriteria();

    void setPageSize(int pageSize);

    void goToRow(int firstRow);

    int getFirstRow();

    int getPageSize();
}
