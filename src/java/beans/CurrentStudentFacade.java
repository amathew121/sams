/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Course;
import entities.CurrentStudent;
import entities.ProgramCourse;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ashish
 */
@Stateless
public class CurrentStudentFacade extends AbstractFacade<CurrentStudent> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CurrentStudentFacade() {
        super(CurrentStudent.class);
    }

    public List<CurrentStudent> getCurrentStudentByDiv(ProgramCourse programCourse, short semester, String div, short batch) {
        List <CurrentStudent> l = new ArrayList();
        
        Query q = em.createNamedQuery("CurrentStudent.findUltimate");
        q.setParameter("programCourse", programCourse);
        q.setParameter("batch", batch);
        q.setParameter("division", div);
        q.setParameter("semester", semester);
        l = q.getResultList();
        return l;
    }
        public List<CurrentStudent> getCurrentStudentByDivTheory(ProgramCourse programCourse, short semester, String div) {
        List <CurrentStudent> l = new ArrayList();
        
        Query q = em.createNamedQuery("CurrentStudent.findUltimateTheory");
        q.setParameter("programCourse", programCourse);
        q.setParameter("division", div);
        q.setParameter("semester", semester);
        l = q.getResultList();
        return l;
    }
    
}
