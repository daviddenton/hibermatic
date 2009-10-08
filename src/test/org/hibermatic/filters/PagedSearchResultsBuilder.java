package org.hibermatic.filters;

import java.util.List;
import static java.util.Arrays.asList;

public class PagedSearchResultsBuilder {
    private static final Object OBJECT = new Object();
    private List pageOfResults = asList(OBJECT, OBJECT, OBJECT, OBJECT, OBJECT);
    private int pageSize = pageOfResults.size();
    private int pageNumber = 1;
    private int totalResultCount = 20;

    public static PagedSearchResultsBuilder aPagedSearchResultsBuilder() {
        return new PagedSearchResultsBuilder();
    }

    public PagedSearchResultsBuilder onPage(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public PagedSearchResultsBuilder withTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
        return this;
    }

    public PagedSearchResultsBuilder withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PagedSearchResultsBuilder withResultsOnPage(List pageOfResults) {
        this.pageOfResults = pageOfResults;
        return this;
    }

    public PagedSearchResults build() {
        return new PagedSearchResults((pageNumber-1) * pageSize, pageSize, pageOfResults, totalResultCount);
    }
}
