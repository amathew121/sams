/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty.lecture;

import beans.AbstractFacade;
import entities.users.Department;
import entities.subject.faculty.lecture.ViewLectureNotTaken;
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
public class ViewLectureNotTakenFacade extends AbstractFacade<ViewLectureNotTaken> {

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
    public ViewLectureNotTakenFacade() {
        super(ViewLectureNotTaken.class);
    }

    
    /**
     * get list of all faculty subject entities taught by the specified faculty
     *
     * @param s
     * @return
     */
    public List<ViewLectureNotTaken> getViewLNTByFaculty(String s) {
        Query q = em.createNamedQuery("ViewLectureNotTaken.findByIdFaculty");
        q.setParameter("idFaculty", s);
        List<ViewLectureNotTaken> l = q.getResultList();
        count = l.size();
        return l;
    }

    

    /**
     * get all the faculty subject entities in the specified department
     *
     * @param s
     * @return
     */
    public List<ViewLectureNotTaken> getViewByDept(String s, String program, int semester) {
        Query q = em.createNamedQuery("ViewLectureNotTaken.findByIdProgramCourse");
        q.setParameter("course", s);
        q.setParameter("program", program);
        q.setParameter("sem", semester);
        
        List<ViewLectureNotTaken> l = q.getResultList();
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
