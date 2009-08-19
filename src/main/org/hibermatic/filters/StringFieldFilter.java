package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

public class StringFieldFilter extends AbstractFieldFilter {

    public StringFieldFilter(String associationPath) {
        super(associationPath);
    }

    public StringFieldFilter roughly(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ilike(getPropertyName(), "%" + fieldValue.trim() + "%"));
        }
        return this;
    }

    public StringFieldFilter isNotEqual(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ne(getPropertyName(), fieldValue.trim()));
        }
        return this;
    }

    public StringFieldFilter exactly(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.eq(getPropertyName(), fieldValue.trim()));
        }
        return this;
    }

    public StringFieldFilter caseInsensitiveEquals(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.ilike(getPropertyName(), fieldValue.trim()));
        }
        return this;
    }

    private boolean isValid(String fieldValue) {
        return fieldValue != null && !"".equals(fieldValue);
    }

    public FieldFilter startsWith(String fieldValue) {
        if (isValid(fieldValue)) {
            setExpression(Restrictions.like(getPropertyName(), fieldValue.trim() + "%"));
        }
        return this;
    }

}
