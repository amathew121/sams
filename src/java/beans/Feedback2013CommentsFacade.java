/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.FacultySubject;
import entities.Feedback2013Comments;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Feedback2013comments entity
 * @author piit
 */
@Stateless
public class Feedback2013CommentsFacade extends AbstractFacade<Feedback2013Comments> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the Feedback2013comments EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * create Feedback2013comments entity
     */
    public Feedback2013CommentsFacade() {
        super(Feedback2013Comments.class);
    }
    
    /**
     * gets feedback comments for the specified faculty subject
     * @param idFacSub
     * @return
     */
    public List getByUserName(FacultySubject idFacSub) {
        Query q = em.createNamedQuery("Feedback2013Comments.findByIdFacultySubject");
        q.setParameter("idFacultySubject", idFacSub);
        return q.getResultList();
    }

}
