package org.hibermatic.filters;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.HibernateCriteriaManipulation;
import org.hibernate.criterion.Order;

/**
 * Filter for adding sort direction for field
 */
public class SortByFieldFilter extends AbstractFieldFilter {

    private Boolean ascending;

    public SortByFieldFilter(String associationPath) {
        super(associationPath, CriteriaSpecification.LEFT_JOIN);
    }

    public SortByFieldFilter(String associationPath, int joinType) {
        super(associationPath, joinType);
    }

    /**
     * Apply descending sort direction to field
     *
     * @return
     */
    public FieldFilter withDescendingOrder() {
        return withOrderDirection(false);
    }

    /**
     * Apply ascending sort direction to field
     *
     * @return
     */
    public FieldFilter withAscendingOrder() {
        return withOrderDirection(true);
    }

    /**
     * Apply custom sort direction to field. Useful when the sort direction is only known at runtime.
     *
     * @param ascending
     * @return
     */
    public FieldFilter withOrderDirection(boolean ascending) {
        this.ascending = ascending;
        return this;
    }

    @Override
    public void applyTo(DetachedCriteria criteria) {
        if (ascending != null) {
            // Ensure alias has been made with nested property so we can apply sorting on it
            HibernateCriteriaManipulation.ensureJoinTypeFromAssociation(criteria, getAssociationPath(), getJoinType());
            criteria.addOrder(ascending ? Order.asc(getAssociationPath()) : Order.desc(getAssociationPath()));
        }
    }
}
