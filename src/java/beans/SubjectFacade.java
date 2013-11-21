/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.ProgramCourse;
import entities.Subject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ashish
 */
@Stateless
public class SubjectFacade extends AbstractFacade<Subject> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubjectFacade() {
        super(Subject.class);
    }
    
    public List<Subject> findSubjectBySemester(ProgramCourse programCourse, short semester) {
        Query q = em.createNamedQuery("Subject.findBySemester");
        q.setParameter("semester", semester);
        q.setParameter("programCourse", programCourse);
        return q.getResultList();
    }
    public List<Subject> findSubjectByPC(ProgramCourse programCourse) {
        Query q = em.createNamedQuery("Subject.findByProgramCourse");
        q.setParameter("programCourse", programCourse);
        return q.getResultList();
    }
    
}
