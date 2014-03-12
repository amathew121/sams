/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Subject;
import entities.SubjectSyllabus;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author phcoe
 */
@Stateless
public class SubjectSyllabusFacade extends AbstractFacade<SubjectSyllabus> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubjectSyllabusFacade() {
        super(SubjectSyllabus.class);
    }
    
    public List<SubjectSyllabus> getByIdSubject(Subject sub){
        Query q = em.createNamedQuery("SubjectSyllabus.findByIdSubject");
        q.setParameter("idSubject", sub);
        return q.getResultList();
    }
    
}
