/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty.lecture;

import beans.AbstractFacade;
import entities.subject.faculty.lecture.Attendance;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.lecture.Lecture;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for Attendance entity
 * @author Ashish
 */
@Stateless
public class AttendanceFacade extends AbstractFacade<Attendance> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the Attendance EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Creates Attendance EJB
     */
    public AttendanceFacade() {
        super(Attendance.class);
    }
    
    /**
     * Gets attendance records for the specified Lecture
     * @param lec Lecture Entity
     * @return Attendance Records
     */
    public List<Attendance> getAttendanceByFSLec(Lecture lec) {
        List <Attendance> l = new ArrayList();
        Query q = em.createNamedQuery("Attendance.findByIdLecture");
        q.setParameter("idLecture", lec);
        l = q.getResultList();
        return l;
    }
}
