package org.hibermatic.projections;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.SimpleProjection;
import org.hibernate.type.Type;

public class ProjectionBuilders {

    private ProjectionBuilders() {
    }

    public static Projection countWhen(ConditionClause conditionClause) {
        return new CustomProjection("SUM", conditionClause);
    }

    public static Projection when(ConditionClause conditionClause) {
        return new CustomProjection("", conditionClause);
    }

    private static class CustomProjection extends SimpleProjection {

        private final ConditionClause clause;
        private final String function;

        public CustomProjection(String function, ConditionClause clause) {
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
