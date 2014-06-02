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
 * Enterprise JavaBean for reviewcomments entity
 * @author piit
 */
@Stateless
public class ReviewCommentsFacade extends AbstractFacade<ReviewComments> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for the reviewcomments EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates reviewcomments EJB
     */
    public ReviewCommentsFacade() {
        super(ReviewComments.class);
    }
    
    /**
     * get reviews for the specified faculty subject and type 
     * @param idFacSub
     * @param type
     * @return
     */
    public List getByIdFacSubType(FacultySubject idFacSub, short type) {
        Query q = em.createNamedQuery("ReviewComments.findByIdFacSubType");
        q.setParameter("reviewType", type);
        q.setParameter("idFacultySubject", idFacSub);
        
        return q.getResultList();
    }
    
}
