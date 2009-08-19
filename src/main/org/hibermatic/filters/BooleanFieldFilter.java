package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

public class BooleanFieldFilter extends AbstractFieldFilter {

    public BooleanFieldFilter(String associationPath) {
        super(associationPath);
    }

    public BooleanFieldFilter is(boolean fieldValue) {
        setExpression(Restrictions.eq(getPropertyName(), fieldValue));
        return this;
    }
}
