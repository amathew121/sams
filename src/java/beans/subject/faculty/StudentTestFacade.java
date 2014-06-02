/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty;

import beans.AbstractFacade;
import entities.subject.faculty.lecture.CurrentStudent;
import entities.subject.faculty.StudentTest;
import entities.subject.Subject;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for studenttest entity
 * @author piit
 */
@Stateless
public class StudentTestFacade extends AbstractFacade<StudentTest> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for studenttest EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates studenttest EJB
     */
    public StudentTestFacade() {
        super(StudentTest.class);
    }

    /**
     * get testmarks for the specified student subject and test(1 or 2)
     * @param idCurrentStudent
     * @param idSubject
     * @param test
     * @return
     */
    public StudentTest getStudentTestMarks(CurrentStudent idCurrentStudent, Subject idSubject, short test) {
        Query q = em.createNamedQuery("StudentTest.findByIdCurrentStudentIdSubjectTest");
        q.setParameter("idCurrentStudent", idCurrentStudent);
        q.setParameter("idSubject", idSubject);
        q.setParameter("test", test);
        try {
            return (StudentTest) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
