/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject;

import beans.AbstractFacade;
import entities.subject.ProgramCourse;
import entities.subject.Subject;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Subject entity
 * @author Ashish
 */
@Singleton
public class SubjectFacade extends AbstractFacade<Subject> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for subject EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates subject EJB
     */
    public SubjectFacade() {
        super(Subject.class);
    }
    
    /**
     * gets the list of subjects for the specified course and semester
     * @param programCourse
     * @param semester
     * @return
     */
    public List<Subject> findSubjectBySemester(ProgramCourse programCourse, short semester) {
        Query q = em.createNamedQuery("Subject.findBySemester");
        q.setParameter("semester", semester);
        q.setParameter("programCourse", programCourse);
        return q.getResultList();
    }
    
    public List<Subject> findSubjectBySemesterHide(ProgramCourse programCourse, short semester) {
        boolean subHide = true;
        Query q = em.createNamedQuery("Subject.findBySemesterHide");
        q.setParameter("semester", semester);
        q.setParameter("programCourse", programCourse);
        q.setParameter("subHide", subHide);
        return q.getResultList();
    }

    /**
     * gets the list of subjects for the specified course
     * @param programCourse
     * @return
     */
    public List<Subject> findSubjectByPC(ProgramCourse programCourse) {
        Query q = em.createNamedQuery("Subject.findByProgramCourse");
        q.setParameter("programCourse", programCourse);
        return q.getResultList();
    }
    
}
