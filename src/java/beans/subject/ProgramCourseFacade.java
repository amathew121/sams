 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.ProgramCourse;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *Enterprise JavaBean for programcourse entity
 * @author Ashish
 */
@Singleton
public class ProgramCourseFacade extends AbstractFacade<ProgramCourse> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the programcourse EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates programcourse EJB
     */
    public ProgramCourseFacade() {
        super(ProgramCourse.class);
    }
    
}
