/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.FacultySubject;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ashish
 */
@Stateless
public class FacultySubjectFacade extends AbstractFacade<FacultySubject> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacultySubjectFacade() {
        super(FacultySubject.class);
    }
    
}
