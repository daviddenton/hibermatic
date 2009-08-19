package org.hibermatic.filters;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import static org.hibernate.criterion.HibernateCriteriaManipulation.ensureJoinTypeFromAssociation;

/**
 * General superclass of all library FieldFilters. Uses assocation name and enforces a specified join type to relationships
 */
public abstract class AbstractFieldFilter implements FieldFilter {
    private final String associationPath;
    private int joinType;
    private Criterion expression;

    /**
     * Create a filter using the applicable object association path and defaulting to a LEFT JOIN for join relationships (ie. association paths containing a period)
     *
     * @param associationPath
     */
    public AbstractFieldFilter(String associationPath) {
        this(associationPath, CriteriaSpecification.LEFT_JOIN);
    }

    /**
     * Create a filter using the applicable object association path and join type used for join relationships (ie. association paths containing a period)
     *
     * @param associationPath
     * @param joinType
     */
    protected AbstractFieldFilter(String associationPath, int joinType) {
        this.associationPath = associationPath;
        this.joinType = joinType;
    }

    /**
     * Set the actual expression Criterion to be applied.
     *
     * @param expression
     */
    protected void setExpression(Criterion expression) {
        this.expression = expression;
    }

    /**
     * Returns the base property name for the assocation path. Eg. For "a.b.c", "c" will be returned.
     *
     * @return
     */
    protected String getPropertyName() {
        if (this.associationPath != null && this.associationPath.indexOf('.') != -1) {
            String[] subFieldNames = splitFieldName(this.associationPath);
            return subFieldNames[subFieldNames.length - 1];
        }
        return this.associationPath;
    }

    /**
     * @return the associationPath
     */
    protected String getAssociationPath() {
        return associationPath;
    }

    public void applyTo(DetachedCriteria criteria) {
        if (expression != null) {
            ensureJoinTypeFromAssociation(criteria, associationPath, joinType).add(expression);
        }
    }

    private static String[] splitFieldName(String fieldFilterName) {
        return fieldFilterName.split("\\.");
    }

    protected int getJoinType() {
        return joinType;
    }
}
