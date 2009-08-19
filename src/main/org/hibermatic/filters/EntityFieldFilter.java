package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

public class EntityFieldFilter extends AbstractFieldFilter {

    public EntityFieldFilter(String associationPath) {
        super(associationPath);
    }

    public EntityFieldFilter eq(Object object) {
        if (isValid(object)) {
            setExpression(Restrictions.eq(getPropertyName(), object));
        }
        return this;
    }

    public EntityFieldFilter ne(Object object) {
        if (isValid(object)) {
            setExpression(Restrictions.ne(getPropertyName(), object));
        }
        return this;
    }

    private boolean isValid(Object fieldValue) {
        return fieldValue != null;
    }
}
