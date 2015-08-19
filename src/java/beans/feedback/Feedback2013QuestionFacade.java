/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.feedback;

import beans.AbstractFacade;
import entities.feedback.Feedback2013Question;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Feedback2013question entity
 * @author piit
 */
@Stateless
public class Feedback2013QuestionFacade extends AbstractFacade<Feedback2013Question> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the feedback2013question EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates feedback2013question EJB
     */
    public Feedback2013QuestionFacade() {
        super(Feedback2013Question.class);
    }
    
    public List<Feedback2013Question> getFBQuesNew(Integer ver){
        Query q = em.createNamedQuery("Feedback2013Question.findByQVersion");
        q.setParameter("qver", ver);
        return q.getResultList();
    }
    
}
