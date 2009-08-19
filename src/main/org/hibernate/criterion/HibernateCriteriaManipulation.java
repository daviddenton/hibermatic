package org.hibernate.criterion;

import org.hibernate.Criteria;
import org.hibernate.impl.CriteriaImpl;

import java.util.Iterator;

public class HibernateCriteriaManipulation {

    public static Criteria ensureJoinTypeFromAssociation(DetachedCriteria criteria, final String associationPath, int joinType) {
        CriteriaImpl rootCriteria = criteria.getCriteriaImpl();

        if (isAJoinRelationship(associationPath)) {
            String[] subFieldNames = associationPath.split("\\.");
            Criteria subCriteria = rootCriteria;
            String assocationPath = null;
            for (int i = 0; i < subFieldNames.length - 1; i++) {
                assocationPath = (i == 0) ? subFieldNames[i] : assocationPath + "." + subFieldNames[i];
                subCriteria = findNamedSubcriteriaFrom(rootCriteria, assocationPath, joinType);
            }
            return subCriteria;
        }
        return rootCriteria;
    }

    private static boolean isAJoinRelationship(String associationPath) {
        return associationPath != null && associationPath.indexOf('.') != -1;
    }

    private static Criteria findNamedSubcriteriaFrom(CriteriaImpl criteria, String associationPath, int joinType) {
        for (Iterator iterator = criteria.iterateSubcriteria(); iterator.hasNext();) {
            CriteriaImpl.Subcriteria traversedSubcriteria = (CriteriaImpl.Subcriteria) iterator.next();
            if (traversedSubcriteria.getPath().equals(associationPath)) {
                return traversedSubcriteria;
            }
        }
        return criteria.createCriteria(associationPath, associationPath, joinType);
    }

}
