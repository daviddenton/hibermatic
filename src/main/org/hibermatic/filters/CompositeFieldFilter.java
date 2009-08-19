package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class CompositeFieldFilter implements FieldFilter {
    private final List<FieldFilter> filters;

    public CompositeFieldFilter(List<FieldFilter> filters) {
        this.filters = filters;
    }

    public void applyTo(DetachedCriteria criteria) {
        for (FieldFilter filter : filters) {
            filter.applyTo(criteria);
        }
    }
}
