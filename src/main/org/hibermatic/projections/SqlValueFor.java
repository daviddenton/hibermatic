package org.hibermatic.projections;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SqlValueFor {

    private SqlValueFor() {}

    public static ConditionClause date(final Date date) {
        return new ConditionClause() {
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
                return "TO_DATE('" + new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss").format(date) + "','yyyy/mm/dd:HH24:mi:ss')";
            }
        };
    }                                                           
    
    public static ConditionClause string(final String value) {
        return new ConditionClause() {
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
                return new StringBuilder().append("'").append(value).append("'").toString();
            }
        };
    }
}
