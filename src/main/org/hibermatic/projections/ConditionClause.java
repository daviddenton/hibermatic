package org.hibermatic.projections;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;

/**
 * Condition clause for Hibernate projections. Similar to Criterion API - may be merged in future.
 */
public interface ConditionClause {
    String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery);
}
