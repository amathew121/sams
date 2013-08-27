/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.AttendanceReport;
import entities.Course;
import java.util.ArrayList;
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
public class AttendanceReportFacade extends AbstractFacade<AttendanceReport> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttendanceReportFacade() {
        super(AttendanceReport.class);
    }

    public List<Object[]> getStudentAttendanceByFS(int idFacSub) {
        Query q = em.createNamedQuery("AttendanceReport.findByIdFacultySubject");
        q.setParameter("idFacultySubject", idFacSub);
        List<Object[]> l = q.getResultList();
        return l;
    }

    public List<Object[]> getStudentAttendanceBySubDivSem(Course course, String division, short semester, int idSubject) {
        Query q = em.createNamedQuery("AttendanceReport.findByIdSubjectSemesterDivisionCount");
        q.setParameter("idSubject", idSubject);
        q.setParameter("semester", semester);
        q.setParameter("division", division);
        q.setParameter("idCourse", course.getIdCourse());
        q.setParameter("fsBatch", 0);
        List<Object[]> l = q.getResultList();
        return l;
    }

    public List<Integer> getStudentAttendanceCountByFS(Course course, String division, short semester, int idSubject) {
        Query q = em.createNamedQuery("AttendanceReport.findByIdSubjectSemesterDivisionCount");
        q.setParameter("idSubject", idSubject);
        q.setParameter("semester", semester);
        q.setParameter("division", division);
        q.setParameter("idCourse", course.getIdCourse());

        return q.getResultList();
    }
}