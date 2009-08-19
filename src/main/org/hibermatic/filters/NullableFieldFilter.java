package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

/**
 * Filters for object prescence.
 */
public class NullableFieldFilter extends AbstractFieldFilter {
    public NullableFieldFilter(String associationPath) {
        super(associationPath);
    }

    public NullableFieldFilter(String associationPath, HibernateJoinTypeMapping joinType) {
        super(associationPath, joinType);
    }

    /**
     * Filters that field is not null
     *
     * @return
     */
    public FieldFilter isNotNull() {
        setExpression(Restrictions.isNotNull(getPropertyName()));
        return this;
    }

    /**
     * Filters that field is null
     *
     * @return
     */
    public FieldFilter isNull() {
        setExpression(Restrictions.isNull(getPropertyName()));
        return this;
    }
}
