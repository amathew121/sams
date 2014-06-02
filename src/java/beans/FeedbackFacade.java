/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.OldFbPi;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Feedback entity
 * @author piit
 */
@Stateless
public class FeedbackFacade extends AbstractFacade<OldFbPi>  {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the feedback EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * creates feedback EJB
     */
    public FeedbackFacade() {
        super (OldFbPi.class);
    }

    /**
     * gets feedback ratings for the specified faculty for the year 2012-13
     * @param user
     * @return
     */
    public List<OldFbPi> findByStaff(String user) {
        Query q = em.createNamedQuery("OldFbPi.findByFacId");
        q.setParameter("facId", user);
        List<OldFbPi> l = new ArrayList <OldFbPi> ();
        l = q.getResultList();
        return l;
        
                
    }



}
