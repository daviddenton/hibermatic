package org.hibermatic.filters;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.HibernateCriteriaManipulation;
import org.hibernate.criterion.Order;

public class SortByFieldFilter extends AbstractFieldFilter {
    private boolean ascending;

    public SortByFieldFilter(String associationPath) {
        super(associationPath);
    }

    public SortByFieldFilter withDescendingOrder() {
        return withOrderDirection(false);
    }

    public SortByFieldFilter withAscendingOrder() {
        return withOrderDirection(true);
    }

    public SortByFieldFilter withOrderDirection(boolean ascending) {
        this.ascending = ascending;
        return this;
    }

    @Override
    public void applyTo(DetachedCriteria criteria) {
        // Ensure alias has been made with nested property so we can apply sorting on it
        HibernateCriteriaManipulation.ensureJoinTypeFromAssociation(criteria, getAssociationPath(), CriteriaSpecification.LEFT_JOIN);
        criteria.addOrder(ascending ? Order.asc(getAssociationPath()) : Order.desc(getAssociationPath()));
    }
}
