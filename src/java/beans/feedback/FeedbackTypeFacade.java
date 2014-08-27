/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.feedback;

import beans.AbstractFacade;
import entities.feedback.FeedbackType;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Faculty entity
 * @author piit
 */
@Stateless
public class FeedbackTypeFacade extends AbstractFacade<FeedbackType> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the Faculty EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackTypeFacade() {
        super(FeedbackType.class);
    }
    
    public List<FeedbackType> findAllDesc(){
        List <FeedbackType> l = new ArrayList();
        
        Query q = em.createNamedQuery("FeedbackType.findByIdFeedbackTypeDesc");
        l = q.getResultList();
        return l;
       
    }
    
}
