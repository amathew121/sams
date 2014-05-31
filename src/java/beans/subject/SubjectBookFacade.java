/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.Subject;
import entities.subject.SubjectBook;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ashish Mathew
 */
@Singleton
public class SubjectBookFacade extends AbstractFacade<SubjectBook> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubjectBookFacade() {
        super(SubjectBook.class);
    }

    public List<SubjectBook> getByIdSubject(Subject sub) {
        Query q = em.createNamedQuery("SubjectBook.findByIdSubject");
        q.setParameter("idSubject", sub);
        return q.getResultList();
    }
    
}
