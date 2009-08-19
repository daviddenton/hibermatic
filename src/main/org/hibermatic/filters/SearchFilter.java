package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;

public interface SearchFilter<T> {

    DetachedCriteria toCriteria();
    DetachedCriteria toCountCriteria();

    void setPageSize(int pageSize);
    void goToRow(int firstRow);

    int getFirstRow();
    int getPageSize();
}
