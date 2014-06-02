/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.AttendanceView;
import entities.FacultySubject;
import entities.Lecture;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for AttendanceView entity
 * @author Ashish
 */
@Stateless
public class AttendanceViewFacade extends AbstractFacade<AttendanceView> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets entity manager for the AttendanceView EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * create AttendanceView EJB
     */
    public AttendanceViewFacade() {
        super(AttendanceView.class);
    }
    
    /**
     * gets attendance list for the specified faculty subject
     * @param facSub
     * @return
     */
    public List<AttendanceView> getAttendanceByFS(FacultySubject facSub){
        List <AttendanceView> l= new ArrayList();
        Query q = em.createNamedQuery("AttendanceView.findByIdFacultySubject");
        q.setParameter("idFacultySubject", facSub.getIdFacultySubject());
        l = q.getResultList();
        return l;
    }

    /**
     * gets no of students attended for the specified lecture and faculty subject
     * @param facSub
     * @param lec
     * @return
     */
    public Long getAttendanceByFSCount(FacultySubject facSub, Lecture lec) {
        //TODO: ID FAC SUB PARAMETER IS NOT REQUIRED
        Query q = em.createNamedQuery("AttendanceView.findByIdFacultySubjectCount");
        q.setParameter("idFacultySubject", facSub.getIdFacultySubject());
        q.setParameter("idLecture", lec.getIdLecture());
        Long count = (Long) q.getSingleResult();
        return count;
    }
}
