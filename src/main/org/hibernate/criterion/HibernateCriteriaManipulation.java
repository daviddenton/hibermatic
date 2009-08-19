package org.hibernate.criterion;

import org.hibernate.Criteria;
import org.hibernate.impl.CriteriaImpl;
import org.hibermatic.filters.JoinType;

import java.util.Iterator;

/**
 * Effectively a patch for Hibernate to enable selection of join type for DetachedCriteria relationships. By default, Hibernate only allows INNER joins.
 */
public class HibernateCriteriaManipulation {

    /**
     * Returns the Hibernate criteria object for the passed association path. If this is a joined relationship (ie. the association contains a period), the subcriteria
     * relating to the association is returned instead, with the Join Type specified to the passed root criteria object.
     * @param rootCriteria - criteria for the base object in the association path tree
     * @param associationPath - the association path to the desired object criteria
     * @param joinType the join type to enforce on the association
     * @return the lowest level criteria for the passed association path
     */
    public static Criteria ensureJoinTypeFromAssociation(DetachedCriteria rootCriteria, final String associationPath, JoinType joinType) {
        CriteriaImpl returnedCriteria = rootCriteria.getCriteriaImpl();

        if (isAJoinRelationship(associationPath)) {
            String[] subFieldNames = associationPath.split("\\.");
            Criteria subCriteria = returnedCriteria;
            String assocationPath = null;
            for (int i = 0; i < subFieldNames.length - 1; i++) {
                assocationPath = (i == 0) ? subFieldNames[i] : assocationPath + "." + subFieldNames[i];
                subCriteria = findNamedSubcriteriaFrom(returnedCriteria, assocationPath, joinType);
            }
            return subCriteria;
        }
        return returnedCriteria;
    }

    private static boolean isAJoinRelationship(String associationPath) {
        return associationPath != null && associationPath.indexOf('.') != -1;
    }

    private static Criteria findNamedSubcriteriaFrom(CriteriaImpl criteria, String associationPath, JoinType joinType) {
        for (Iterator iterator = criteria.iterateSubcriteria(); iterator.hasNext();) {
            CriteriaImpl.Subcriteria traversedSubcriteria = (CriteriaImpl.Subcriteria) iterator.next();
            if (traversedSubcriteria.getPath().equals(associationPath)) {
                return traversedSubcriteria;
            }
        }
        return criteria.createCriteria(associationPath, associationPath, joinType.getHibernateJoinType());
    }

}
