/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Faculty;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Enterprise JavaBean for Faculty entity
 * @author piit
 */
@Stateless
public class FacultyFacade extends AbstractFacade<Faculty> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the Faculty EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Creates Faculty EJB
     */
    public FacultyFacade() {
        super(Faculty.class);
    }
    
}
