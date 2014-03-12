/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Subject;
import entities.SubjectOutcome;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author phcoe
 */
@Stateless
public class SubjectOutcomeFacade extends AbstractFacade<SubjectOutcome> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubjectOutcomeFacade() {
        super(SubjectOutcome.class);
    }

    public List getByIdSubject(Subject sub) {
        Query q = em.createNamedQuery("SubjectOutcome.findByIdSubjcet");
        q.setParameter("idSubject", sub);
        return q.getResultList();
    }
    
}
