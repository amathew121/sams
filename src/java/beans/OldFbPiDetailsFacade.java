/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.OldFbPiDetails;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for oldfbpidetails entity
 * @author piit
 */
@Stateless
public class OldFbPiDetailsFacade extends AbstractFacade<OldFbPiDetails> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the oldfbpidetails EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates oldfbpidetails EJB
     */
    public OldFbPiDetailsFacade() {
        super(OldFbPiDetails.class);
    }
    
    /**
     * gets the comments for the specified faculty, division, batch, subject for the year 2012-13
     * @param userName
     * @param div
     * @param ftype
     * @param batch
     * @param subID
     * @return
     */
    public List <OldFbPiDetails> getByFS(String userName, String div,short ftype, short batch, String subID) {
        List <OldFbPiDetails> l = new ArrayList ();
        Query q = em.createNamedQuery("OldFbPiDetails.findByFS");
        q.setParameter("division", div);
        q.setParameter("batch", batch);
        q.setParameter("facId", userName);
        q.setParameter("subId", subID);
        q.setParameter("ftype", ftype);
        l = q.getResultList();
        return l;
    }
    
    
}
