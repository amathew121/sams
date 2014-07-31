/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.users;

import beans.AbstractFacade;
import entities.users.Coordinator;
import entities.users.Faculty;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Coordinator Entity
 * @author piit
 */
@Stateless
public class CoordinatorFacade extends AbstractFacade<Coordinator> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the Coordinator EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Creates Coordinator EJB
     */
    public CoordinatorFacade() {
        super(Coordinator.class);
    }
    
    /**
     * returns coordinator entity if the specified faculty is a coordinator
     * @param idFaculty
     * @return
     */
    public Coordinator findByUser(Faculty idFaculty)
    {
        Query q = em.createNamedQuery("Coordinator.findByFaculty");
        q.setParameter("faculty", idFaculty);
        if(q.getResultList().size() >0)
        return (Coordinator) q.getResultList().get(0);
        else
            return null;
    }
    
}
