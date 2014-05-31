/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.Subject;
import entities.subject.SubjectEvaluation;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ashish Mathew
 */
@Singleton
public class SubjectEvaluationFacade extends AbstractFacade<SubjectEvaluation> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubjectEvaluationFacade() {
        super(SubjectEvaluation.class);
    }

    public List<SubjectEvaluation> getByIdSubject(Subject sub) {
        Query q = em.createNamedQuery("SubjectEvaluation.findByIdSubject");
        q.setParameter("idSubject", sub);
        return q.getResultList();
    }
    
}
