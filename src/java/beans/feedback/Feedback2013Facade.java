/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.feedback;

import beans.AbstractFacade;
import entities.subject.faculty.FacultySubject;
import entities.feedback.Feedback2013;
import entities.feedback.FeedbackType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for feedback2013 entity
 * @author piit
 */
@Stateless
public class Feedback2013Facade extends AbstractFacade<Feedback2013> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the feedback2013 EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates feedback2013 EJB
     */
    public Feedback2013Facade() {
        super(Feedback2013.class);
    }
    
    public List<Feedback2013> getByUserName(FacultySubject idFacultySubject, FeedbackType fType) {
        Query q = em.createNamedQuery("Feedback2013.findByIdFacultySubject");
        q.setParameter("idFacultySubject", idFacultySubject);
        q.setParameter("fType", fType);
        return q.getResultList();
    }
    
    /**
     * gets feedback rating for the specifed faculty subject group by question
     * @param idFacultySubject
     * @return
     */
    public List getRating(FacultySubject idFacultySubject) {
        Query q = em.createNamedQuery("Feedback2013.findByRating");
        q.setParameter("idFacultySubject", idFacultySubject);
        List l = q.getResultList();
        return l;
    }
    
    public List getFeedbackByQuestion(int qid, int type) {
        Query q = em.createNamedQuery("Feedback2013.findByQuestion");
        q.setParameter("qid", qid);
        q.setParameter("idFeedbackType", type);
        return q.getResultList();
    }
}
