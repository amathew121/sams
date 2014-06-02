/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.Subject;
import entities.subject.SubjectObjective;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for subjectobjective entity
 * @author piit
 */
@Singleton
public class SubjectObjectiveFacade extends AbstractFacade<SubjectObjective> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * get entity manager for subjectobjective EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates subjectobjective EJB
     */
    public SubjectObjectiveFacade() {
        super(SubjectObjective.class);
    }

    /**
     * gets objectives for the specified subjects
     * @param subject
     * @return
     */
    public List<SubjectObjective> getByIdSubject(Subject subject) {
        Query q = em.createNamedQuery("SubjectObjective.findByIdSubject");
        q.setParameter("idSubject", subject);
        return q.getResultList();
        
    }
    
}
