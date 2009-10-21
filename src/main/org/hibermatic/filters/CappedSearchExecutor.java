package org.hibermatic.filters;

import org.hibernate.Criteria;

import java.util.List;

class CappedSearchExecutor implements SearchExecutor {
    private int firstRow = 0;
    private int maxResults;

    CappedSearchExecutor(int maxResults) {
        this.maxResults = maxResults;
    }

    public List search(Criteria criteria) {
        return criteria.setFirstResult(firstRow).setMaxResults(maxResults).list();
    }
}
