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
 * Enterprise JavaBean for feedback2013 entity
 * @author piit
 */
@Stateless
public class Feedback2013Facade extends AbstractFacade<Feedback2013> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the feedback2013 EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates feedback2013 EJB
     */
    public Feedback2013Facade() {
        super(Feedback2013.class);
    }
    
    /**
     * get feedback rating for the specifed faculty subject
     * @param idFacultySubject
     * @return
     */
    public List<Feedback2013> getByUserName(FacultySubject idFacultySubject) {
        Query q = em.createNamedQuery("Feedback2013.findByIdFacultySubject");
        q.setParameter("idFacultySubject", idFacultySubject);
        return q.getResultList();
    }
    
    /**
     * gets feedback rating for the specifed faculty subject group by question
     * @param idFacultySubject
     * @return
     */
    public List getRating(FacultySubject idFacultySubject) {
        Query q = em.createNamedQuery("Feedback2013.findByRating");
        q.setParameter("idFacultySubject", idFacultySubject);
        List l = q.getResultList();
        return l;
    }
}
