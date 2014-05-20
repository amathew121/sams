/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Faculty;
import entities.FacultySubject;
import entities.FacultySubjectView;
import entities.ProgramCourse;
import entities.Subject;
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
public class FacultySubjectFacade extends AbstractFacade<FacultySubject> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacultySubjectFacade() {
        super(FacultySubject.class);
    }

    public FacultySubject getFSById(int s) {
        Query q = em.createNamedQuery("FacultySubject.findByIdFacultySubject");
        q.setParameter("idFacultySubject", s);
        //List <FacultySubjectView> l = q.getResultList();
        List<FacultySubject> fl = q.getResultList();
        //count = l.size();
        FacultySubject l = (FacultySubject) fl.get(0);
        return l;
    }

    public List<FacultySubject> getFSByIdFac(Faculty fac) {
        Query q = em.createNamedQuery("FacultySubject.findByIdFaculty");
        q.setParameter("idFaculty", fac);
        List<FacultySubject> l = q.getResultList();
        return l;


    }
    
    public List<FacultySubject> getFSBySubject(Subject idSubject) {
        Query q = em.createNamedQuery("FacultySubject.findByIdSubject");
        q.setParameter("idSubject", idSubject);
        List<FacultySubject> l = q.getResultList();
        return l;
    }

    public Faculty getFacById(String idFac) {
        Query q = em.createNamedQuery("Faculty.findByIdFaculty");
        q.setParameter("idFaculty", idFac);
        return (Faculty) q.getSingleResult();
    }
    
    public  List<FacultySubject> getFSByYear(Faculty idFaculty,int yr, boolean even) {
        Query q;
        if (even) {
            q = em.createNamedQuery("FacultySubject.findByYearEven");
        }
        else {
            q = em.createNamedQuery("FacultySubject.findByYearOdd");
        }
        q.setParameter("yr", yr);
        q.setParameter("idFaculty", idFaculty);
        return q.getResultList();
    }

    public FacultySubject getFSBySemDivBatchSub(String division, short semester, short batch, Subject idSubject) {
        Query q;
        if (semester %2 ==0)
            q = em.createNamedQuery("FacultySubject.findBySemDivBatchSubEven");
        else
            q = em.createNamedQuery("FacultySubject.findBySemDivBatchSub");
 
        q.setParameter("division", division);
        q.setParameter("batch", batch);
        q.setParameter("idSubject", idSubject);
        try {
        List<FacultySubject> l = q.getResultList();
        return l.get(0);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        public List<FacultySubject> getFSBySemDivSub(short semester, String division, ProgramCourse programCourse) {
        Query q = em.createNamedQuery("FacultySubject.findBySemDivPC");
        q.setParameter("division", division);
        q.setParameter("semester", semester);
        q.setParameter("programCourse", programCourse);
        return q.getResultList();
   
    }
}
