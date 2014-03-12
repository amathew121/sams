/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.FacultySubject;
import entities.ReviewComments;
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
public class ReviewCommentsFacade extends AbstractFacade<ReviewComments> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReviewCommentsFacade() {
        super(ReviewComments.class);
    }
    
    public List getByIdFacSubType(FacultySubject idFacSub, short type) {
        Query q = em.createNamedQuery("ReviewComments.findByIdFacSubType");
        q.setParameter("reviewType", type);
        q.setParameter("idFacultySubject", idFacSub);
        
        return q.getResultList();
    }
    
}
