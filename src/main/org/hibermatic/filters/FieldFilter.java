package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;

/**
 * Interface for grouping a particular set of filtering criteria.
 */
public interface FieldFilter extends Serializable {

    /**
     * Apply the configuration to the passed DetachedCriteria object.
     *
     * @param criteria
     */
    void applyTo(DetachedCriteria criteria);
}
