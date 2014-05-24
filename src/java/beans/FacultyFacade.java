/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Faculty;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author piit
 */
@Stateless
public class FacultyFacade extends AbstractFacade<Faculty> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacultyFacade() {
        super(Faculty.class);
    }
    
    public Faculty findFacultyByToken(String token){
        Query q = em.createNamedQuery("Faculty.findByToken");
        q.setParameter("token", token);
        if(q.getResultList().size()>0)
            return (Faculty)q.getResultList().get(0);
        else
            return null;
    }
    
}
