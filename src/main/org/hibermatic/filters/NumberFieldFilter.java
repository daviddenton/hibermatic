package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

import java.util.List;

public class NumberFieldFilter extends AbstractFieldFilter {

    public NumberFieldFilter(String associationPath) {
        super(associationPath);
    }

    public NumberFieldFilter exactly(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.eq(getPropertyName(), fieldValue));
        }
        return this;
    }

    public NumberFieldFilter withValues(List<Number> fieldValues) {
        if (isValid(fieldValues)) {
            setExpression(Restrictions.in(getPropertyName(), fieldValues));
        }
        return this;
    }

    private boolean isValid(Number fieldValue) {
        return fieldValue != null;
    }

    private boolean isValid(List<Number> fieldValues) {
        return fieldValues != null && !fieldValues.isEmpty();
    }

    public NumberFieldFilter lessThan(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.lt(getPropertyName(), fieldValue));
        }
        return this;
    }

    public NumberFieldFilter greaterThan(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.gt(getPropertyName(), fieldValue));
        }
        return this;
    }

    public NumberFieldFilter lessThanEqual(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.le(getPropertyName(), fieldValue));
        }
        return this;
    }

    public NumberFieldFilter moreThanEqual(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ge(getPropertyName(), fieldValue));
        }
        return this;
    }
}
