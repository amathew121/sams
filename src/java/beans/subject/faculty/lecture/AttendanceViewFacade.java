/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty.lecture;

import beans.AbstractFacade;
import entities.subject.faculty.lecture.AttendanceView;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.lecture.Lecture;
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
public class AttendanceViewFacade extends AbstractFacade<AttendanceView> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttendanceViewFacade() {
        super(AttendanceView.class);
    }
    
    public List<AttendanceView> getAttendanceByFS(FacultySubject facSub){
        List <AttendanceView> l= new ArrayList();
        Query q = em.createNamedQuery("AttendanceView.findByIdFacultySubject");
        q.setParameter("idFacultySubject", facSub.getIdFacultySubject());
        l = q.getResultList();
        return l;
    }
    public Long getAttendanceByFSCount(FacultySubject facSub, Lecture lec) {
        Query q = em.createNamedQuery("AttendanceView.findByIdFacultySubjectCount");
        q.setParameter("idFacultySubject", facSub.getIdFacultySubject());
        q.setParameter("idLecture", lec.getIdLecture());
        Long count = (Long) q.getSingleResult();
        return count;
    }
}
