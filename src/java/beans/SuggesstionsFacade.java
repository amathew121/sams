/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Faculty;
import entities.Suggesstions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for suggestions entity
 * @author piit
 */
@Stateless
public class SuggesstionsFacade extends AbstractFacade<Suggesstions> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for suggestions EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates suggestions EJB
     */
    public SuggesstionsFacade() {
        super(Suggesstions.class);
    }
    
    /**
     * gets suggestions for the specified faculty
     * @param idFaculty
     * @return
     */
    public List<Suggesstions> getSuggestionsByUserName(Faculty idFaculty){
        Query q  = em.createNamedQuery("Suggesstions.findByIdFaculty");
        q.setParameter("idFaculty", idFaculty);
        return q.getResultList();
    }
}
