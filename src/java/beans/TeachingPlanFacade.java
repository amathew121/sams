/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.FacultySubject;
import entities.TeachingPlan;
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
public class TeachingPlanFacade extends AbstractFacade<TeachingPlan> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeachingPlanFacade() {
        super(TeachingPlan.class);
    }
    
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
    
}
