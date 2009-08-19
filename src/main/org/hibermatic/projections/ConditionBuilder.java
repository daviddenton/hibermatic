package org.hibermatic.projections;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;

/**
 * Provides a DSL around projection condition construction. For use with ProjectionBuilders
 */
public class ConditionBuilder {
    private final ConditionClause firstClause;

    public ConditionBuilder(ConditionClause firstClause) {
        this.firstClause = firstClause;
    }

    /**
     * Creates a Conditionbuilder for the passed property/association path.
     * @param propertyName
     * @return
     */
    public static ConditionBuilder property(String propertyName) {
        return new ConditionBuilder(new PropertyNameConditionClause(propertyName));
    }

    public ConditionClause isEqualTo(ConditionClause secondClause) {
        return new OperatorConditionClause(firstClause, "=", secondClause);
    }

    public ConditionClause isNotEqualTo(ConditionClause secondClause) {
        return new OperatorConditionClause(firstClause, "<>", secondClause);
    }

    public ConditionClause isBefore(ConditionClause secondClause) {
        return isLessThan(secondClause);
    }

    public ConditionClause isAfterOrOn(ConditionClause secondClause) {
        return isGreaterThanOrEqualTo(secondClause);
    }

    public ConditionClause isAfter(ConditionClause secondClause) {
        return isGreaterThan(secondClause);
    }

    public ConditionClause isLessThan(ConditionClause secondClause) {
        return new OperatorConditionClause(firstClause, "<", secondClause);
    }

    public ConditionClause isLessThanOrEqualTo(ConditionClause secondClause) {
        return new OperatorConditionClause(firstClause, "<=", secondClause);
    }

    public ConditionClause isGreaterThan(ConditionClause secondClause) {
        return new OperatorConditionClause(firstClause, ">", secondClause);
    }

    public ConditionClause isGreaterThanOrEqualTo(ConditionClause secondClause) {
        return new OperatorConditionClause(firstClause, ">=", secondClause);
    }

    public static ConditionClause and(final ConditionClause first, final ConditionClause second) {
        return new ConditionClause() {
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
                return new StringBuilder()
                        .append(first.toSqlString(criteria, criteriaQuery))
                        .append(" AND ")
                        .append(second.toSqlString(criteria, criteriaQuery))
                        .toString();
            }
        };
    }

    public ConditionClause isBetweenUpperExclusive(ConditionClause lower, ConditionClause upper) {
        return and(isGreaterThanOrEqualTo(lower), isLessThan(upper));
    }

    public ConditionClause isBetweenLowerExclusive(ConditionClause lower, ConditionClause upper) {
        return and(isGreaterThan(lower), isLessThanOrEqualTo(upper));
    }

    public ConditionClause isBetween(ConditionClause lower, ConditionClause upper) {
        return and(isGreaterThanOrEqualTo(lower), isLessThanOrEqualTo(upper));
    }

    public ConditionClause isNull() {
         return new ConditionClause() {
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
                return new StringBuilder()
                        .append(firstClause.toSqlString(criteria, criteriaQuery))
                        .append(" IS NULL")
                        .toString();
            }
        };
    }

    public ConditionClause isNotNull() {
         return new ConditionClause() {
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
                return new StringBuilder()
                        .append(firstClause.toSqlString(criteria, criteriaQuery))
                        .append(" IS NOT NULL")
                        .toString();
            }
        };
    }

    private static class PropertyNameConditionClause implements ConditionClause {
        private final String propertyName;

        public PropertyNameConditionClause(String propertyName) {
            this.propertyName = propertyName;
        }

        public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
            return criteriaQuery.getColumn(criteria, propertyName);
        }
    }

    private static class OperatorConditionClause implements ConditionClause {
        private final ConditionClause firstClause;
        private final ConditionClause secondClause;
        private final String operator;

        public OperatorConditionClause(ConditionClause firstClause, String operator, ConditionClause secondClause) {
            this.firstClause = firstClause;
            this.secondClause = secondClause;
            this.operator = operator;
        }

        public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
            return new StringBuilder().append(firstClause.toSqlString(criteria, criteriaQuery))
                    .append(operator)
                    .append(secondClause.toSqlString(criteria, criteriaQuery)).toString();
        }
    }

}
