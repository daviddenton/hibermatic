package org.hibermatic.filters;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import static org.hibernate.criterion.HibernateCriteriaManipulation.ensureJoinTypeFromAssociation;

public abstract class AbstractFieldFilter implements FieldFilter {
    private final String associationPath;
    private Criterion expression;

    public AbstractFieldFilter(String associationPath) {
        this.associationPath = associationPath;
    }

    protected void setExpression(Criterion expression) {
        this.expression = expression;
    }

    protected String getPropertyName() {
        if (this.associationPath != null && this.associationPath.indexOf('.') != -1) {
            String[] subFieldNames = splitFieldName(this.associationPath);
            return subFieldNames[subFieldNames.length - 1];
        }
        return this.associationPath;
    }

    protected String getAssociationPath() {
        return associationPath;
    }

    public void applyTo(DetachedCriteria criteria) {
        if (expression != null) {
            ensureJoinTypeFromAssociation(criteria, associationPath, CriteriaSpecification.LEFT_JOIN).add(expression);
        }
    }

    private static String[] splitFieldName(String fieldFilterName) {
        return fieldFilterName.split("\\.");
    }
}
