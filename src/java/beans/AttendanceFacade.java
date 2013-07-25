/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Attendance;
import entities.FacultySubject;
import entities.Lecture;
import java.util.ArrayList;
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
public class AttendanceFacade extends AbstractFacade<Attendance> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttendanceFacade() {
        super(Attendance.class);
    }
    
    public List<Attendance> getAttendanceByFSLec(Lecture lec) {
        List <Attendance> l = new ArrayList();
        Query q = em.createNamedQuery("Attendance.findByIdLecture");
        q.setParameter("idLecture", lec);
        l = q.getResultList();
        return l;
    }
}
