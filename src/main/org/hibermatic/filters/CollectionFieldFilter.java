package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

import java.util.Collection;

/**
 * FieldFilter for collections of objects mapped by Hibernate.
 */
public class CollectionFieldFilter<T> extends AbstractFieldFilter {

    public CollectionFieldFilter(String associationPath) {
        super(associationPath);
    }

    public CollectionFieldFilter(String associationPath, HibernateJoinTypeMapping joinType) {
        super(associationPath, joinType);
    }

    /**
     * Filters that field belongs to passed Collection
     *
     * @param objects
     * @return
     */
    public FieldFilter in(Collection<T> objects) {
        if (isValid(objects)) {
            setExpression(Restrictions.in(getPropertyName(), objects));
        }
        return this;
    }

    /**
     * Filters that field does not belong to passed Collection
     *
     * @param objects
     * @return
     */
    public FieldFilter notIn(Collection<T> objects) {
        if (isValid(objects)) {
            setExpression(Restrictions.not(Restrictions.in(getPropertyName(), objects)));
        }
        return this;
    }

    /**
     * Filters that field belongs to passed Array
     *
     * @param objects
     * @return
     */
    public FieldFilter in(T[] objects) {
        if (isValid(objects)) {
            setExpression(Restrictions.in(getPropertyName(), objects));
        }
        return this;
    }

    /**
     * Filters that field does not belong to passed Array
     *
     * @param objects
     * @return
     */
    public FieldFilter notIn(T[] objects) {
        if (isValid(objects)) {
            setExpression(Restrictions.not(Restrictions.in(getPropertyName(), objects)));
        }
        return this;
    }

    private boolean isValid(Object fieldValue) {
        return fieldValue != null;
    }
}