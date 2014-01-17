/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Department;
import entities.Faculty;
import entities.FacultySubjectView;
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
public class FacultySubjectViewFacade extends AbstractFacade<FacultySubjectView> {

    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;
    private int count = 0;

    public int getCount() {
        return count;
    }

    @Override
    public int count() {


        return count; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacultySubjectViewFacade() {
        super(FacultySubjectView.class);
    }

    public List<FacultySubjectView> getFSViewByIdEven(String s) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdFacultyEven");
        q.setParameter("idFaculty", s);
        List<FacultySubjectView> l = q.getResultList();
        count = l.size();
        return l;
    }
    public List<FacultySubjectView> getFSViewById(String s) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdFaculty");
        q.setParameter("idFaculty", s);
        List<FacultySubjectView> l = q.getResultList();
        count = l.size();
        return l;
    }
    public List<FacultySubjectView> getFSViewByIdGroup() {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdFacultyGroup");
        List<FacultySubjectView> l = q.getResultList();
        count = l.size();
        return l;
    }

    public List<FacultySubjectView> getFSViewByDept(String s) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdCourse");
        q.setParameter("idCourse", s);
        List<FacultySubjectView> l = q.getResultList();
        return l;
    }

    public List<FacultySubjectView> getFSViewByDeptSub(String sub, String dept) {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdCourseSubject");
        q.setParameter("idCourse", dept);
        q.setParameter("subjectCode", sub);
        List<FacultySubjectView> l = q.getResultList();
        return l;
    }

    public List<Department> getDepartment() {
        List<Department> l = new ArrayList();
        Query q = em.createNamedQuery("Department.findAll");
        l = q.getResultList();
        return l;
    }
}
