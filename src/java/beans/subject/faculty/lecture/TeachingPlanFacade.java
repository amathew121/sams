/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty.lecture;

import beans.AbstractFacade;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.lecture.TeachingPlan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for teachingplan entity
 * @author Ashish
 */
@Stateless
public class TeachingPlanFacade extends AbstractFacade<TeachingPlan> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for teachingplan EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates techingplan EJB
     */
    public TeachingPlanFacade() {
        super(TeachingPlan.class);
    }
    
    /**
     * TODO: Move to Faculty Subject facade. Possible duplicate
     * @param s
     * @return
     */
    public FacultySubject getFSById(int s) {
        Query q = em.createNamedQuery("FacultySubject.findByIdFacultySubject");
        q.setParameter("idFacultySubject", s);
        //List <FacultySubjectView> l = q.getResultList();
        List<FacultySubject> fl = q.getResultList();
        //count = l.size();
        System.out.println(fl.toString());
        FacultySubject l = (FacultySubject) fl.get(0);
        return l;
    }
    
    /**
     * gets teachingplan for the specified facultysubject
     * @param f
     * @return
     */
    public List<TeachingPlan> getTeachingPlanByFS(FacultySubject f)
    {
        List <TeachingPlan> l = new ArrayList();
        Query q = em.createNamedQuery("TeachingPlan.findByIdFacultySubject");
        q.setParameter("idFacultySubject", f);
        l =q.getResultList();
        return l;
    }
    
}
