package org.hibermatic.filters;

import org.hibernate.Criteria;

import java.util.List;

class NavigableSearchExecutor implements SearchExecutor {
    private int firstRow;

    public List search(Criteria criteria) {
        return criteria.setFirstResult(firstRow).list();
    }

    /**
     * Sets the first returned row of the resultset. Zero indexed, which is also the minimum.
     *
     * @param newFirstRow
     */
    public void goToRow(int newFirstRow) {
        this.firstRow = newFirstRow < 0 ? 0 : newFirstRow;
    }

    /**
     * @return the first row number of the resultset to return. Zero indexed.
     */
    public int firstRow() {
        return firstRow;
    }
}
