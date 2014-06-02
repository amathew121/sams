/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.Program;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Enterprise JavaBean for program entity
 * @author Ashish
 */
@Singleton
public class ProgramFacade extends AbstractFacade<Program> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the program EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates program EJB
     */
    public ProgramFacade() {
        super(Program.class);
    }
    
}
