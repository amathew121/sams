/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.feedback;

import beans.AbstractFacade;
import entities.subject.faculty.FacultySubject;
import entities.feedback.Feedback2013Comments;
import entities.feedback.FeedbackType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Feedback2013comments entity
 * @author piit
 */
@Stateless
public class Feedback2013CommentsFacade extends AbstractFacade<Feedback2013Comments> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the Feedback2013comments EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * create Feedback2013comments entity
     */
    public Feedback2013CommentsFacade() {
        super(Feedback2013Comments.class);
    }
    
    public List getByUserName(FacultySubject idFacSub, FeedbackType fType) {
        Query q = em.createNamedQuery("Feedback2013Comments.findByIdFacultySubject");
        q.setParameter("idFacultySubject", idFacSub);
        q.setParameter("fType", fType);
        return q.getResultList();
    }

}
