/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.users;

import beans.AbstractFacade;
import entities.users.Faculty;
import entities.users.Reviewer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *Enterprise JavaBean for reviewer entity
 * @author piit
 */
@Stateless
public class ReviewerFacade extends AbstractFacade<Reviewer> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the reviewer EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates reviewer EJB
     */
    public ReviewerFacade() {
        super(Reviewer.class);
    }
    
    /**
     * get the list of all reviewer entities grouped by faculty
     * @return
     */
    public List<Reviewer> getReviewers() {
        Query q = em.createNamedQuery("Reviewer.findAllGroupByFaculty");
        return q.getResultList();
                
    }

    /**
     * get the list of all reviewer entities for the specified faculty
     * @param idFaculty
     * @return
     */
    public List<Reviewer> getItemsByFaculty(Faculty idFaculty) {
        Query q = em.createNamedQuery("Reviewer.findByFaculty");
        q.setParameter("idFaculty", idFaculty);
        return q.getResultList();

    }
}
