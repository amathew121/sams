/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Suggesstions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
