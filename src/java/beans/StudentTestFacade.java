/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.CurrentStudent;
import entities.StudentTest;
import entities.Subject;
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
public class StudentTestFacade extends AbstractFacade<StudentTest> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentTestFacade() {
        super(StudentTest.class);
    }

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
