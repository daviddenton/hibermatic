package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

/**
 * FieldFilter for objects mapped by Hibernate.
 */
public class EntityFieldFilter extends AbstractFieldFilter {

    public EntityFieldFilter(String associationPath) {
        super(associationPath);
    }

    public EntityFieldFilter(String associationPath, HibernateJoinTypeMapping joinType) {
        super(associationPath, joinType);
    }

    /**
     * Filters for same object equality
     *
     * @param object
     * @return
     */
    public FieldFilter eq(Object object) {
        if (isValid(object)) {
            setExpression(Restrictions.eq(getPropertyName(), object));
        }
        return this;
    }

    /**
     * Filters for different object equality
     *
     * @param object
     * @return
     */
    public FieldFilter ne(Object object) {
        if (isValid(object)) {
            setExpression(Restrictions.ne(getPropertyName(), object));
        }
        return this;
    }

    private boolean isValid(Object fieldValue) {
        return fieldValue != null;
    }
}
