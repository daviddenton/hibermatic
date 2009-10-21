package org.hibermatic.filters;

import org.hibernate.Criteria;

import java.util.List;

class PagingSearchExecutor implements SearchExecutor {
    private int pageNumber = 1;
    private int pageSize;

    PagingSearchExecutor(int pageSize) {
        if (pageSize < 1) throw new IllegalArgumentException("Can't have a page size of less than 1");
        this.pageSize = pageSize;
    }

    public void goToNextPage() {
        pageNumber++;
    }

    public void goToPreviousPage() {
        if (pageNumber > 1) pageNumber--;
    }

    public int currentPage() {
        return pageNumber;
    }

    public int pageSize() {
        return pageSize;
    }

    public List search(Criteria criteria) {
        int firstResult = (pageNumber * pageSize) - 1;
        return criteria.setFirstResult(firstResult).setMaxResults(pageSize).list();
    }

    public void goToPage(int newPageNumber) {
        this.pageNumber = (newPageNumber < 1) ? 1 : newPageNumber;
    }
}