/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.FacultySubject;
import entities.Feedback2013;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author piit
 */
@Stateless
public class Feedback2013Facade extends AbstractFacade<Feedback2013> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Feedback2013Facade() {
        super(Feedback2013.class);
    }
    
    public List<Feedback2013> getByUserName(FacultySubject idFacultySubject) {
        Query q = em.createNamedQuery("Feedback2013.findByIdFacultySubject");
        q.setParameter("idFacultySubject", idFacultySubject);
        return q.getResultList();
    }
    
    public List getRating(FacultySubject idFacultySubject) {
        Query q = em.createNamedQuery("Feedback2013.findByRating");
        q.setParameter("idFacultySubject", idFacultySubject);
        List l = q.getResultList();
        return l;
    }
}
