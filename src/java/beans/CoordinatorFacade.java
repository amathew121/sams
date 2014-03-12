/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Coordinator;
import entities.Faculty;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author phcoe
 */
@Stateless
public class CoordinatorFacade extends AbstractFacade<Coordinator> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoordinatorFacade() {
        super(Coordinator.class);
    }
    
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
