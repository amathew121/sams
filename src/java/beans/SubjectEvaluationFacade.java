/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Subject;
import entities.SubjectEvaluation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author piit
 */
@Stateless
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
