/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty;

import beans.AbstractFacade;
import entities.subject.faculty.ViewpiltAvg;
import entities.users.Department;
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
public class ViewPiLTAvgFacade extends AbstractFacade<ViewpiltAvg> {

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
    public ViewPiLTAvgFacade() {
        super(ViewpiltAvg.class);
    }

    
   

        /**
     * get list of all faculty subject entities taught by the specified faculty
     *
     * @param s
     * @return
     */
    public List<ViewpiltAvg> getViewPiltAvgByFaculty(String s) {
        Query q = em.createNamedQuery("ViewpiltAvg.findByIdFaculty");
        q.setParameter("idFaculty", s);
        List<ViewpiltAvg> l = q.getResultList();
        count = l.size();
        return l;
    }
    

    /**
     * get all the faculty subject entities in the specified department
     *
     * @param s
     * @return
     */
    public List<ViewpiltAvg> getViewByDept(String s, String program, int semester, short batch) {
        Query q;
        if(batch == 0)
            q = em.createNamedQuery("ViewpiltAvg.findByIdProgramCourseTheory");
        else
            q = em.createNamedQuery("ViewpiltAvg.findByIdProgramCoursePractical");
        q.setParameter("course", s);
        q.setParameter("program", program);
        //q.setParameter("sem", semester);
        
        List<ViewpiltAvg> l = q.getResultList();
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
