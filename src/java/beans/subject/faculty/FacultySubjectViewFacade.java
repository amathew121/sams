/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty;

import entities.subject.Program;
import beans.AbstractFacade;
import entities.users.Department;
import entities.users.Faculty;
import entities.subject.faculty.FacultySubjectView;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for FacultySubjectView Entity
 *
 * @author Ashish
 */
@Stateless
public class FacultySubjectViewFacade extends AbstractFacade<FacultySubjectView> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;
    private int count = 0;

    /**
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    //TODO: DELETE
    @Override
    public int count() {


        return count; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FacultySubjectViewFacade() {
        super(FacultySubjectView.class);
    }

    /**
     * get list of all faculty subject entities taught by the specified faculty
     * in the even semester
     *
     * @param s
     * @return
     */
    public List<FacultySubjectView> getFSViewByIdCurrent(String s) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdFacultyCurrent");
        q.setParameter("idFaculty", s);
        List<FacultySubjectView> l = q.getResultList();
        count = l.size();
        return l;
    }

    /**
     * get list of all faculty subject entities taught by the specified faculty
     *
     * @param s
     * @return
     */
    public List<FacultySubjectView> getFSViewById(String s) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdFaculty");
        q.setParameter("idFaculty", s);
        List<FacultySubjectView> l = q.getResultList();
        count = l.size();
        return l;
    }

    /**
     * gets list of all the faculty subject entities grouped by faculty TODO:
     * Find usages
     *
     * @return
     */
    public List<FacultySubjectView> getFSViewByIdGroup() {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdFacultyGroup");
        List<FacultySubjectView> l = q.getResultList();
        count = l.size();
        return l;
    }

    /**
     * get all the faculty subject entities in the specified department
     *
     * @param s
     * @return
     */
    public List<FacultySubjectView> getFSViewByDept(String s, String program, int ac_yr,int sem) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdCourseFinal");
        q.setParameter("idCourse", s);
        q.setParameter("program", program);
        q.setParameter("ac_yr", ac_yr);
        q.setParameter("sem", sem);
        List<FacultySubjectView> l = q.getResultList();
        return l;
    }

    /**
     * get list of faculty teaching for the specified subject in the specified
     * department
     *
     * @param sub
     * @param dept
     * @return
     */
    public List<FacultySubjectView> getFSViewByDeptSub(String sub, String dept) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdCourseSubject");
        q.setParameter("idCourse", dept);
        q.setParameter("subjectCode", sub);
        List<FacultySubjectView> l = q.getResultList();
        return l;
    }

    /**
     * gets list of department TODO: refractor to department facade
     *
     * @return
     */
    public List<Department> getDepartment() {
        List<Department> l = new ArrayList();
        Query q = em.createNamedQuery("Department.findAll");
        l = q.getResultList();
        return l;
    }
}
