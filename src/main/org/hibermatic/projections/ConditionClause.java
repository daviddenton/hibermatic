package org.hibermatic.projections;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;

public interface ConditionClause {
    String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery);
}
