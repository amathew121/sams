/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.FacultySubjectView;
import entities.OldFbPi;
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
public class OldFbPiFacade extends AbstractFacade<OldFbPi> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OldFbPiFacade() {
        super(OldFbPi.class);
    }

    public List<OldFbPi> findByUserName(String s) {
        Query q = em.createNamedQuery("OldFbPi.findByFacId");
        q.setParameter("facId", s);
        List <OldFbPi> l = q.getResultList();
        return l;    }
    
    public List<OldFbPi> findByUserNameGroup() {
        Query q = em.createNamedQuery("OldFbPi.findAllGroupByUser");
        List<OldFbPi> l = q.getResultList();
        return l;
    }
    
}
