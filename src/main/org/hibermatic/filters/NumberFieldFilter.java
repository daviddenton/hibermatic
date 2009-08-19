package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

import java.util.Collection;

/**
 * Filters for Number types
 */
public class NumberFieldFilter extends AbstractFieldFilter {

    public NumberFieldFilter(String associationPath) {
        super(associationPath);
    }

    public NumberFieldFilter(String associationPath, HibernateJoinTypeMapping joinType) {
        super(associationPath, joinType);
    }

    /**
     * Filters for exact value
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter exactly(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.eq(getPropertyName(), fieldValue));
        }
        return this;
    }

    /**
     * Filters that field value belongs to passed collection
     *
     * @param fieldValues
     * @return
     */
    public FieldFilter withValues(Collection<Number> fieldValues) {
        if (isValid(fieldValues)) {
            setExpression(Restrictions.in(getPropertyName(), fieldValues));
        }
        return this;
    }

    private boolean isValid(Number fieldValue) {
        return fieldValue != null;
    }

    private boolean isValid(Collection<Number> fieldValues) {
        return fieldValues != null && !fieldValues.isEmpty();
    }

    /**
     * Filters for less than value
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter lessThan(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.lt(getPropertyName(), fieldValue));
        }
        return this;
    }

    /**
     * Filters for greater than value
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter greaterThan(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.gt(getPropertyName(), fieldValue));
        }
        return this;
    }

    /**
     * Filters for less than equal value
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter lessThanEqual(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.le(getPropertyName(), fieldValue));
        }
        return this;
    }

    /**
     * Filters for greater than equal value
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter moreThanEqual(Number fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ge(getPropertyName(), fieldValue));
        }
        return this;
    }
}
