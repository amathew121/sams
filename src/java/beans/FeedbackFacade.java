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
 *
 * @author phcoe
 */
@Stateless
public class FeedbackFacade extends AbstractFacade<OldFbPi>  {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public FeedbackFacade() {
        super (OldFbPi.class);
    }

    public List<OldFbPi> findByStaff(String user) {
        Query q = em.createNamedQuery("OldFbPi.findByFacId");
        q.setParameter("facId", user);
        List<OldFbPi> l = new ArrayList <OldFbPi> ();
        l = q.getResultList();
        return l;
        
                
    }



}
