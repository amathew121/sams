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
 *
 * @author piit
 */
@Stateless
public class OldFbPiDetailsFacade extends AbstractFacade<OldFbPiDetails> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OldFbPiDetailsFacade() {
        super(OldFbPiDetails.class);
    }
    
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
