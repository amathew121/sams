/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty.lecture;

import beans.AbstractFacade;
import entities.subject.faculty.lecture.Attendance;
import entities.subject.faculty.lecture.CurrentStudent;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.lecture.Lecture;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Ashish
 */
@Stateless
public class LectureFacade extends AbstractFacade<Lecture> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LectureFacade() {
        super(Lecture.class);
    }
    
    public List<Lecture> getLectureByIdFaculty(FacultySubject f) {
        
        
        Query q = em.createNamedQuery("Lecture.findByIdFacultySubject");
        q.setParameter("idFacultySubject", f);
        List<Lecture> l = q.getResultList();
        return l;
    }
    
    public List<Lecture> getLectureByIdFacultyDateRange(FacultySubject f, Date startDate, Date endDate) {


        Query q = em.createNamedQuery("Lecture.findByLectureDateRange");
        q.setParameter("idFacultySubject", f);
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        q.setMaxResults(10);
        List<Lecture> l = q.getResultList();
        return l;
    }
    public List<Lecture> getLectureByIdFacultyDateRange(FacultySubject f, Date startDate) {


        Query q = em.createNamedQuery("Lecture.findByLectureDateRangeStart");
        q.setParameter("idFacultySubject", f);
        q.setParameter("startDate", startDate);
        q.setMaxResults(10);
        List<Lecture> l = q.getResultList();
        return l;
    }
    
    
    
    public Lecture getLectureByIdLecture(Integer idLecture) {


        Query q = em.createNamedQuery("Lecture.findByIdLecture");
        q.setParameter("idLecture", idLecture);
        return (Lecture) q.getSingleResult();
    }
        
    public FacultySubject getFSById(int s){
            Query q = em.createNamedQuery("FacultySubject.findByIdFacultySubject");
            q.setParameter("idFacultySubject", s);
            //List <FacultySubjectView> l = q.getResultList();
            List <FacultySubject> fl=  q.getResultList();
            //count = l.size();
            System.out.println(fl.toString());
            FacultySubject l = (FacultySubject) fl.get(0);
            return l;
    }
    
    public List<CurrentStudent> getCurrentStudentByDiv(short semester, String div, short batch) {
        List <CurrentStudent> l = new ArrayList();
        
        Query q = em.createNamedQuery("CurrentStudent.findUltimate");
        q.setParameter("batch", batch);
        q.setParameter("division", div);
        q.setParameter("semester", semester);
        l = q.getResultList();
        return l;
    }
    
    
    
}
