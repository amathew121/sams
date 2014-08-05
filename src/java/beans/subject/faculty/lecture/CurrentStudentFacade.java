/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty.lecture;

import beans.AbstractFacade;
import entities.subject.Course;
import entities.subject.faculty.lecture.CurrentStudent;
import entities.subject.ProgramCourse;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for CurrentStudent entity
 * @author Ashish
 */
@Stateless
public class CurrentStudentFacade extends AbstractFacade<CurrentStudent> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the CurrentStudent EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Creates CurrentStudent EJB
     */
    public CurrentStudentFacade() {
        super(CurrentStudent.class);
    }

    /**
     * Get a list of CurrentStudent for the specified ProgramCourse, batch, division and semester entities
     *
     * @param programCourse
     * @param semester
     * @param div
     * @param batch
     * @param academicYear the value of academicYear
     * @return
     */
    
    public List<CurrentStudent> getCurrentStudentByDiv(ProgramCourse programCourse, short semester, String div, short batch, java.util.Date academicYear) {
        List <CurrentStudent> l = new ArrayList();
        
        Query q = em.createNamedQuery("CurrentStudent.findUltimate");
        q.setParameter("programCourse", programCourse);
        q.setParameter("batch", batch);
        q.setParameter("division", div);
        q.setParameter("semester", semester);
        q.setParameter("academicYear", academicYear);
        l = q.getResultList();
        return l;
    }

    /**
     * Get a list of CurrentStudent for the specified ProgramCourse, division and semester entities
     *
     * @param programCourse
     * @param semester
     * @param div
     * @param academicYear the value of academicYear
     * @return
     */
    
    public List<CurrentStudent> getCurrentStudentByDivTheory(ProgramCourse programCourse, short semester, String div, java.util.Date academicYear) {
        List <CurrentStudent> l = new ArrayList();
        
        Query q = em.createNamedQuery("CurrentStudent.findUltimateTheory");
        q.setParameter("programCourse", programCourse);
        q.setParameter("division", div);
        q.setParameter("semester", semester);
        q.setParameter("academicYear", academicYear);
        l = q.getResultList();
        return l;
    }
    
}
