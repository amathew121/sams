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
 * Enterprise JavaBean for FacultySubject entity
 * @author Ashish
 */
@Stateless
public class FacultySubjectFacade extends AbstractFacade<FacultySubject> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the FacultySubject EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Create FacultySubject EJB
     */
    public FacultySubjectFacade() {
        super(FacultySubject.class);
    }

    /**
     * Gets FacultySubject for the specified FacultySubject Entity
     * @param s
     * @return
     */
    public FacultySubject getFSById(int s) {
        Query q = em.createNamedQuery("FacultySubject.findByIdFacultySubject");
        q.setParameter("idFacultySubject", s);
        //List <FacultySubjectView> l = q.getResultList();
        List<FacultySubject> fl = q.getResultList();
        //count = l.size();
        FacultySubject l = (FacultySubject) fl.get(0);
        return l;
    }

    /**
     * Gets FacultySubject for the specified Faculty
     * @param fac
     * @return
     */
    public List<FacultySubject> getFSByIdFac(Faculty fac) {
        Query q = em.createNamedQuery("FacultySubject.findByIdFaculty");
        q.setParameter("idFaculty", fac);
        List<FacultySubject> l = q.getResultList();
        return l;


    }
    
    /**
     * Gets FacultySubject for the specified Subject
     * @param idSubject
     * @return
     */
    public List<FacultySubject> getFSBySubject(Subject idSubject) {
        Query q = em.createNamedQuery("FacultySubject.findByIdSubject");
        q.setParameter("idSubject", idSubject);
        List<FacultySubject> l = q.getResultList();
        return l;
    }

    /**
     * Gets Faculty for the specified Faculty
     * @param idFac
     * @return
     */
    public Faculty getFacById(String idFac) {
        Query q = em.createNamedQuery("Faculty.findByIdFaculty");
        q.setParameter("idFaculty", idFac);
        return (Faculty) q.getSingleResult();
    }

    /**
     * Gets a list of FacultySubject for the specified division, semester, batch and subject entity
     * @param division
     * @param semester
     * @param batch
     * @param idSubject
     * @return
     */
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

    /**
     * Gets a list of FacultySubject for the specified division, semester and programcourse entity
     * @param semester
     * @param division
     * @param programCourse
     * @return
     */
    public List<FacultySubject> getFSBySemDivSub(short semester, String division, ProgramCourse programCourse) {
        Query q = em.createNamedQuery("FacultySubject.findBySemDivPC");
        q.setParameter("division", division);
        q.setParameter("semester", semester);
        q.setParameter("programCourse", programCourse);
        return q.getResultList();
   
    }
}
