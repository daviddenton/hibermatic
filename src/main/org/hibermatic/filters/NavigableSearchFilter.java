package org.hibermatic.filters;

public class NavigableSearchFilter<B> extends AbstractSearchFilter<NavigableSearchFilter, B> {

    private final NavigableSearchExecutor executor;

    public NavigableSearchFilter(Class<? extends B> entityClass) {
        super(entityClass);
        executor = new NavigableSearchExecutor();
    }

    @Override
    protected SearchExecutor getExecutor() {
        return executor;
    }

      /**
     * Sets the first returned row of the resultset. Zero indexed, which is also the minimum.
     *
     * @param newFirstRow
     */
    public void goToRow(int newFirstRow) {
        executor.goToRow(newFirstRow);
    }

    /**
     * @return the first row number of the resultset to return. Zero indexed.
     */
    public int firstRow() {
        return executor.firstRow();
    }

}
