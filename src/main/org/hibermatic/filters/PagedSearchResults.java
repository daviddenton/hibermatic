package org.hibermatic.filters;

import java.util.List;

public class PagedSearchResults<B> {
    private final int pageSize;
    private final List<B> pageOfResults;
    private final int totalResultCount;
    public final int pageNumber;

    public PagedSearchResults(int pageNumber, int pageSize, List pageOfResults, int totalResultCount) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pageOfResults = pageOfResults;
        this.totalResultCount = totalResultCount;
    }

    public List<B> pageOfResults() {
        return pageOfResults;
    }

    public int totalResultCount() {
        return totalResultCount;
    }

    public int numberOfPages() {
        final int i = (int) Math.ceil((double)totalResultCount / (double)pageSize);
        return i == 0 ? 1 : i;
    }

    public int pageNumber() {
        return pageNumber;
    }

    public boolean hasNextPage() {
        return pageNumber() < numberOfPages();
    }

    public boolean hasPreviousPage() {
        return pageNumber() > 1;
    }

}
