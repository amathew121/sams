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
 *
 * @author Ashish Mathew
 */
@Stateless
public class OldFacultyCommentsFacade extends AbstractFacade<OldFacultyComments> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OldFacultyCommentsFacade() {
        super(OldFacultyComments.class);
    }

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
