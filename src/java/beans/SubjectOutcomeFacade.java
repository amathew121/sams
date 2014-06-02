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
 * Enterprise JavaBean for subjectoutcome entity
 * @author piit
 */
@Stateless
public class SubjectOutcomeFacade extends AbstractFacade<SubjectOutcome> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for subjectoutcome EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates subjectoutcome EJB
     */
    public SubjectOutcomeFacade() {
        super(SubjectOutcome.class);
    }

    /**
     *
     * @param sub
     * @return
     */
    public List getByIdSubject(Subject sub) {
        Query q = em.createNamedQuery("SubjectOutcome.findByIdSubjcet");
        q.setParameter("idSubject", sub);
        return q.getResultList();
    }
    
}
