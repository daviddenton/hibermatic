package org.hibermatic.filters;

import static junit.framework.Assert.assertEquals;
import static org.hibermatic.filters.PagedSearchResultsBuilder.aPagedSearchResultsBuilder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.List;

public class PagedSearchResultsTest {
    private static final Object OBJECT = new Object();
    private static final int TOTAL_RESULT_COUNT = 20;
    private static final List<Object> FIVE_RESULTS = asList(OBJECT, OBJECT, OBJECT, OBJECT, OBJECT);

    @Test
    public void pageOfResults() {
        assertEquals(FIVE_RESULTS, aPagedSearchResultsBuilder().withResultsOnPage(FIVE_RESULTS).build().pageOfResults());
    }

    @Test
    public void totalResultCount() {
        assertEquals(234, aPagedSearchResultsBuilder().withTotalResultCount(234).build().totalResultCount());
    }

    @Test
    public void whenNoEntries() {
        final PagedSearchResults pagedSearchResults =
                aPagedSearchResultsBuilder().
                        withTotalResultCount(0).
                        withResultsOnPage(Collections.EMPTY_LIST).
                        withPageSize(5).
                        onPage(1).
                        build();
        assertResults(pagedSearchResults, false, false, 1, 1);
    }

    @Test
    public void whenExactlyOnePage() {
        final PagedSearchResults pagedSearchResults =
                aPagedSearchResultsBuilder().
                        withTotalResultCount(5).
                        withResultsOnPage(FIVE_RESULTS).
                        withPageSize(5).
                        onPage(1).
                        build();
        assertResults(pagedSearchResults, false, false, 1, 1);
    }

    @Test
    public void whenTwoAndABitPagesAndOnFirstPage() {
        final PagedSearchResults pagedSearchResults =
                aPagedSearchResultsBuilder().
                        withTotalResultCount(11).
                        withResultsOnPage(FIVE_RESULTS).
                        withPageSize(5).
                        onPage(1).
                        build();
        assertResults(pagedSearchResults, true, false, 3, 1);
    }

    @Test
    public void whenTwoAndABitPagesAndOnSecondPage() {
        final PagedSearchResults pagedSearchResults =
                aPagedSearchResultsBuilder().
                        withTotalResultCount(11).
                        withResultsOnPage(FIVE_RESULTS).
                        withPageSize(5).
                        onPage(2).
                        build();
        assertResults(pagedSearchResults, true, true, 3, 2);
    }

    @Test
    public void whenTwoAndABitPagesAndOnLastPage() {
        final PagedSearchResults pagedSearchResults =
                aPagedSearchResultsBuilder().
                        withTotalResultCount(11).
                        withResultsOnPage(asList(OBJECT)).
                        withPageSize(5).
                        onPage(3).
                        build();
        assertResults(pagedSearchResults, false, true, 3, 3);
    }

    private void assertResults(PagedSearchResults pagedSearchResults, boolean next, boolean previous, int totalPages, int currentPage) {
        assertEquals("next", next, pagedSearchResults.hasNextPage());
        assertEquals("previous", previous, pagedSearchResults.hasPreviousPage());
        assertEquals("totalpages", totalPages, pagedSearchResults.numberOfPages());
        assertEquals("currentpage", currentPage, pagedSearchResults.pageNumber());
    }

}
