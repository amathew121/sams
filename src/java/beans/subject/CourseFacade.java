/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.Course;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Enterprise JavaBean for Course entity
 * @author Ashish
 */
@Singleton
public class CourseFacade extends AbstractFacade<Course> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the Course EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Creates Course EJB
     */
    public CourseFacade() {
        super(Course.class);
    }
    
}
