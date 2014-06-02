/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.feedback;

import beans.AbstractFacade;
import entities.feedback.OldFacultyComments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for oldfacultycomments entity
 * @author piit
 */
@Stateless
public class OldFacultyCommentsFacade extends AbstractFacade<OldFacultyComments> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the oldfacultycomments EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates oldfacultycomments EJB
     */
    public OldFacultyCommentsFacade() {
        super(OldFacultyComments.class);
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
    public List<OldFacultyComments> getByFS(String userName, String div, short ftype, short batch, String subID) {
        List<OldFacultyComments> l = new ArrayList();
        if (ftype == 0) {
            Query q = em.createNamedQuery("OldFacultyComments.findByFST");
            q.setParameter("division", div);
            q.setParameter("facId", userName);
            q.setParameter("subId", subID);
            q.setParameter("ftype", ftype);
            l = q.getResultList();

        } else {
            Query q = em.createNamedQuery("OldFacultyComments.findByFS");
            q.setParameter("division", div);
            q.setParameter("batch", batch);
            q.setParameter("facId", userName);
            q.setParameter("subId", subID);
            q.setParameter("ftype", ftype);
            l = q.getResultList();

        }
        return l;
    }
}
