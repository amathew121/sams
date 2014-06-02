/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.Subject;
import entities.subject.SubjectSyllabus;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for subjectsyllabus entity
 * @author piit
 */
@Singleton
public class SubjectSyllabusFacade extends AbstractFacade<SubjectSyllabus> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for subjectsyllabus EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates subjectsyllabus EJB
     */
    public SubjectSyllabusFacade() {
        super(SubjectSyllabus.class);
    }
    
    /**
     *
     * @param sub
     * @return
     */
    public List<SubjectSyllabus> getByIdSubject(Subject sub){
        Query q = em.createNamedQuery("SubjectSyllabus.findByIdSubject");
        q.setParameter("idSubject", sub);
        return q.getResultList();
    }
    
}
