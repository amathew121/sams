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
 *
 * @author piit
 */
@Stateless
public class SuggesstionsFacade extends AbstractFacade<Suggesstions> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SuggesstionsFacade() {
        super(Suggesstions.class);
    }
    
    public List<Suggesstions> getSuggestionsByUserName(Faculty idFaculty){
        Query q  = em.createNamedQuery("Suggesstions.findByIdFaculty");
        q.setParameter("idFaculty", idFaculty);
        return q.getResultList();
    }
}
