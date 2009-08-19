package org.hibermatic.filters;

import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;

public interface FieldFilter extends Serializable {
    void applyTo(DetachedCriteria criteria);
}
