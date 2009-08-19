package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

/**
 * FieldFilter for Boolean types.
 */
public class BooleanFieldFilter extends AbstractFieldFilter {

    public BooleanFieldFilter(String associationPath) {
        super(associationPath);
    }

    public BooleanFieldFilter(String associationPath, int joinType) {
        super(associationPath, joinType);
    }

    public FieldFilter is(boolean fieldValue) {
        setExpression(Restrictions.eq(getPropertyName(), fieldValue));
        return this;
    }
}
