/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.users;

import beans.AbstractFacade;
import entities.users.Student;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ashish
 */
@Stateless
public class StudentFacade extends AbstractFacade<Student> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }
    
}