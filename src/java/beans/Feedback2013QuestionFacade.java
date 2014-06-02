/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Feedback2013Question;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
