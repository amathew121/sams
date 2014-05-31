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
 *
 * @author Ashish Mathew
 */
@Singleton
public class SubjectObjectiveFacade extends AbstractFacade<SubjectObjective> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubjectObjectiveFacade() {
        super(SubjectObjective.class);
    }
    public List<SubjectObjective> getByIdSubject(Subject subject) {
        Query q = em.createNamedQuery("SubjectObjective.findByIdSubject");
        q.setParameter("idSubject", subject);
        return q.getResultList();
        
    }
    
}
