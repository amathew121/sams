/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Faculty;
import entities.Reviewer;
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
public class ReviewerFacade extends AbstractFacade<Reviewer> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReviewerFacade() {
        super(Reviewer.class);
    }
    
    public List<Reviewer> getReviewers() {
        Query q = em.createNamedQuery("Reviewer.findAllGroupByFaculty");
        return q.getResultList();
                
    }
    public List<Reviewer> getItemsByFaculty(Faculty idFaculty) {
        Query q = em.createNamedQuery("Reviewer.findByFaculty");
        q.setParameter("idFaculty", idFaculty);
        return q.getResultList();

    }
}
