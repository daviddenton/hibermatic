package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.Session;

import java.util.List;

/**
 * General interface for all Search Filter implementations.
 * @param <B> The class of the searchable entity
 */
public interface SearchFilter<B> {

    int getPageSize();

    PagedSearchResults<B> currentPage(Session session);

    SearchFilter<B> goToPage(int pageNumber);
    
    SearchFilter<B> goToNextPage();

    SearchFilter<B> goToPreviousPage();

    SearchFilter<B> setPageSize(int pageSize);

    int getFirstRow();

    List<B> search(Session session);

    @Deprecated
    int count(Session session);

    @Deprecated
    SearchFilter<B> goToRow(int firstRow);

    @Deprecated
    DetachedCriteria toCriteria();

    @Deprecated
    DetachedCriteria toCountCriteria();
}
