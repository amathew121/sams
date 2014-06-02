/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.feedback;

import beans.AbstractFacade;
import entities.subject.faculty.FacultySubjectView;
import entities.feedback.OldFbPi;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for oldfbpi entity
 * @author piit
 */
@Stateless
public class OldFbPiFacade extends AbstractFacade<OldFbPi> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;
    
    /**
     * Gets Entity Manager for the oldfbpi EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates oldfbpi EJB
     */
    public OldFbPiFacade() {
        super(OldFbPi.class);
    }

    /**
     * gets performance index for the specified faculty for the year 2012-13
     * @param s
     * @return
     */
    public List<OldFbPi> findByUserName(String s) {
        Query q = em.createNamedQuery("OldFbPi.findByFacId");
        q.setParameter("facId", s);
        List <OldFbPi> l = q.getResultList();
        return l;    }
    
    /**
     * gets list of all performance indexes group by faculty
     * @return
     */
    public List<OldFbPi> findByUserNameGroup() {
        Query q = em.createNamedQuery("OldFbPi.findAllGroupByUser");
        List<OldFbPi> l = q.getResultList();
        return l;
    }
    
}
