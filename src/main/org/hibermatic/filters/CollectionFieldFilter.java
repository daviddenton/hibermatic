package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

import java.util.Collection;

/**
 * FieldFilter for collections of objects mapped by Hibernate.
 */
public class CollectionFieldFilter extends AbstractFieldFilter {

    public CollectionFieldFilter(String associationPath) {
        super(associationPath);
    }

    public CollectionFieldFilter(String associationPath, int joinType) {
        super(associationPath, joinType);
    }

    /**
     * Filters that field belongs to passed Collection
     *
     * @param objects
     * @return
     */
    public FieldFilter in(Collection objects) {
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
    public FieldFilter notIn(Collection objects) {
        if (isValid(objects)) {
            setExpression(Restrictions.not(Restrictions.in(getPropertyName(), objects)));
        }
        return this;
    }

    private boolean isValid(Object fieldValue) {
        return fieldValue != null;
    }
}