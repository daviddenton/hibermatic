package org.hibermatic.filters;

public class CappedSearchFilter<B> extends AbstractSearchFilter<CappedSearchFilter, B> {

    private final CappedSearchExecutor executor;

    public CappedSearchFilter(int maxResults, Class<? extends B> entityClass) {
        super(entityClass);
        executor = new CappedSearchExecutor(maxResults);
    }

    @Override
    protected SearchExecutor getExecutor() {
        return executor;
    }
}