package org.hibermatic.filters;

import org.hibernate.Session;

public class PagedSearchFilter<B> extends AbstractSearchFilter<PagedSearchFilter, B> {

    private final PagingSearchExecutor executor;

    /**
     * A filter with the specified page size for the searchable entity
     *
     * @param pageSize
     * @param entityClass
     */
    public PagedSearchFilter(int pageSize, Class<? extends B> entityClass) {
        super(entityClass);
        executor = new PagingSearchExecutor(pageSize);
    }

    @Override
    protected SearchExecutor getExecutor() {
        return executor;
    }

    /**
     * Returns the nth page of results using the current set of defined filters. Page numbers start at 1.
     *
     * @param pageNumber
     */
    public PagedSearchFilter goToPage(int pageNumber) throws IllegalArgumentException {
        executor.goToPage(pageNumber);
        return this;
    }

    /**
     * Returns the next page of results using the current set of defined filters.
     *
     * @return This filter
     * @throws IllegalArgumentException if page size is unlimited
     */
    public PagedSearchFilter goToNextPage() {
        executor.goToNextPage();
        return this;
    }

    /**
     * Returns the previous page of results using the current set of defined filters. If already on the first page,
     * this has no effect.
     *
     * @return This filter
     * @throws IllegalArgumentException if page size is unlimited
     */
    public PagedSearchFilter goToPreviousPage() {
        executor.goToPreviousPage();
        return this;
    }

    /**
     * Returns the current page of results using the current set of defined filters.
     *
     * @param session
     * @return Page of results of type defined by the entity class.
     * @throws IllegalArgumentException if page size is unlimited
     */
    public PagedSearchResults<B> currentPage(Session session) {

        int count = count(session);

        return new PagedSearchResults<B>(firstRow, pageSize, search(session), count);
    }


    public int numberOfPagesFor(int totalResultCount) {
        final int i = (int) Math.ceil((double)totalResultCount / (double)executor.pageSize());
        return i == 0 ? 1 : i;
    }

    public int pageNumber() {
        return 1+ ((firstRowIndexed+1) / pageSize);
    }

}
