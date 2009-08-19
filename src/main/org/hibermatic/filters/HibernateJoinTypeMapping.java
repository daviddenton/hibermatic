package org.hibermatic.filters;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.sql.JoinFragment;

/**
 * Mapping for common Hibernate join types.
 */
public enum HibernateJoinTypeMapping implements JoinType {
    INNER_JOIN(CriteriaSpecification.INNER_JOIN),
    LEFT_JOIN(CriteriaSpecification.LEFT_JOIN),
    FULL_JOIN(CriteriaSpecification.FULL_JOIN),
    LEFT_OUTER_JOIN(JoinFragment.LEFT_OUTER_JOIN),
    RIGHT_OUTER_JOIN(JoinFragment.RIGHT_OUTER_JOIN),
    ;

    private final int joinType;

    HibernateJoinTypeMapping(int joinType) {
        this.joinType = joinType;
    }

    public int getHibernateJoinType() {
        return joinType;
    }
}
