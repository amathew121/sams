/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.AttendanceReport;
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
    public List<Integer> getStudentAttendanceCountByFS(int idFacSub) {
        Query q = em.createNamedQuery("AttendanceReport.findByIdFacultySubjectCount");
        q.setParameter("idFacultySubject", idFacSub);
        return q.getResultList();
    }
}
