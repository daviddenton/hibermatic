package org.hibermatic.filters;

/**
 * Interface to allow other Hibernate Join Types to be used with this framework. By default, these int values can be found: in org.hibernate.criterion.CriteriaSpecification
 */
public interface JoinType {
    int getHibernateJoinType();
}
