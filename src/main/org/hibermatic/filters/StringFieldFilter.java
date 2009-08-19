package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

/**
 * Filters for String types
 */
public class StringFieldFilter extends AbstractFieldFilter {

    public StringFieldFilter(String associationPath) {
        super(associationPath);
    }

    public StringFieldFilter(String associationPath, HibernateJoinTypeMapping joinType) {
        super(associationPath, joinType);
    }

    /**
     * Filter for partial search ignoring case with wildcards at start and end, with white-space trimmed
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter roughly(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ilike(getPropertyName(), "%" + fieldValue.trim() + "%"));
        }
        return this;
    }

    /**
     * Filter for non-equality, with white-space trimmed
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter isNotEqual(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ne(getPropertyName(), fieldValue.trim()));
        }
        return this;
    }

    /**
     * Filter for equality, with white-space trimmed
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter exactly(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.eq(getPropertyName(), fieldValue.trim()));
        }
        return this;
    }

    /**
     * Filter for equality ignoring case with wildcards at start and end, with white-space trimmed
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter caseInsensitiveEquals(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ilike(getPropertyName(), fieldValue.trim()));
        }
        return this;
    }

    private boolean isValid(String fieldValue) {
        return fieldValue != null && !"".equals(fieldValue);
    }

    /**
     * Filter for partial search with wildcard at end, with white-space trimmed
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter startsWith(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.like(getPropertyName(), fieldValue.trim() + "%"));
        }
        return this;
    }

    /**
     * Filter for partial search with wildcard at start, with white-space trimmed
     *
     * @param fieldValue
     * @return
     */
    public FieldFilter endsWith(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.like(getPropertyName(), fieldValue.trim() + "%"));
        }
        return this;
    }

}
