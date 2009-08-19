package org.hibermatic.projections;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Condition clause factories to create Oracle SQL values.
 */
public class SqlValueFor {

    private SqlValueFor() {}

    /**
     * SQL date down to the second
     * @param date
     * @return
     */
    public static ConditionClause date(final Date date) {
        return new ConditionClause() {
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
                return "TO_DATE('" + new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss").format(date) + "','yyyy/mm/dd:HH24:mi:ss')";
            }
        };
    }

    /**
     * SQL quoted String value
     * @param value
     * @return
     */
    public static ConditionClause string(final String value) {
        return new ConditionClause() {
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
                return new StringBuilder().append("'").append(value).append("'").toString();
            }
        };
    }
}
