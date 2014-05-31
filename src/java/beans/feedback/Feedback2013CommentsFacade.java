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
 *
 * @author Ashish Mathew
 */
@Stateless
public class Feedback2013CommentsFacade extends AbstractFacade<Feedback2013Comments> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

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