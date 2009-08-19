package org.hibermatic.projections;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.SimpleProjection;
import org.hibernate.type.Type;

/**
 * Custom Hibernate Projection builders
 */
public class ProjectionBuilders {

    private ProjectionBuilders() {
    }

    /**
     * Counts all rows when the condition clause is true.
     * @param conditionClause
     * @return
     */
    public static Projection countWhen(ConditionClause conditionClause) {
        return new CaseWhenProjection("SUM", conditionClause);
    }

    /**
     * Results in value of "1" or "0" depending on the condition clause
     * @param conditionClause
     * @return
     */
    public static Projection when(ConditionClause conditionClause) {
        return new CaseWhenProjection("", conditionClause);
    }

    private static class CaseWhenProjection extends SimpleProjection {

        private final ConditionClause clause;
        private final String function;

        public CaseWhenProjection(String function, ConditionClause clause) {
            this.clause = clause;
            this.function = function;
        }

        public String toString() {
            return function + " WHEN " + "(" + clause + ")";
        }

        public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
                throws HibernateException {
            return new Type[]{Hibernate.INTEGER};
        }

        public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery)
                throws HibernateException {
            return new StringBuffer()
                    .append(function)
                    .append("(CASE WHEN ")
                    .append(clause.toSqlString(criteria, criteriaQuery))
                    .append(" THEN 1 ELSE 0 END) as y")
                    .append(loc)
                    .append('_')
                    .toString();
        }

    }

}
