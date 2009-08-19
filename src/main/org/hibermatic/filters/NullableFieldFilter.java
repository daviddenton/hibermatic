package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

public class NullableFieldFilter extends AbstractFieldFilter {
    public NullableFieldFilter(String associationPath) {
        super(associationPath);
    }

    public AbstractFieldFilter isNotNull() {
        setExpression(Restrictions.isNotNull(getPropertyName()));
        return this;
    }

    public AbstractFieldFilter isNull() {
        setExpression(Restrictions.isNull(getPropertyName()));
        return this;
    }
}
