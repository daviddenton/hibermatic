package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;

import java.util.Collection;

/**
 * Convienience class for grouping multiple FieldFilters. When applyTo() is called, filters are applied in the order based on the input Collection type.
 */
public class CompositeFieldFilter implements FieldFilter {
    private final Collection<FieldFilter> filters;

    public CompositeFieldFilter(Collection<FieldFilter> filters) {
        this.filters = filters;
    }

    public void applyTo(DetachedCriteria criteria) {
        for (FieldFilter filter : filters) {
            filter.applyTo(criteria);
        }
    }
}
