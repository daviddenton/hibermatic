package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

import java.util.Collection;

public class ListFieldFilter extends AbstractFieldFilter {

    public ListFieldFilter(String associationPath) {
        super(associationPath);
    }

    public ListFieldFilter in(Collection objects) {
        if (isValid(objects)) {
            setExpression(Restrictions.in(getPropertyName(), objects));
        }
        return this;
    }

    private boolean isValid(Object fieldValue) {
        return fieldValue != null;
    }
}