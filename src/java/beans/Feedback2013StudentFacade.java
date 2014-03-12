/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Coordinator;
import entities.Feedback2013Student;
import entities.ProgramCourse;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author phcoe
 */
@Stateless
public class Feedback2013StudentFacade extends AbstractFacade<Feedback2013Student> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Feedback2013StudentFacade() {
        super(Feedback2013Student.class);
    }
    public List<Feedback2013Student> getStudentList(Coordinator c) {
        Query q = em.createNamedQuery("Feedback2013Student.findBySemDivBatch");
        q.setParameter("semester", c.getCoordinatorPK().getSemester());
        q.setParameter("division", c.getCoordinatorPK().getDivision());
        q.setParameter("programCourse", c.getProgramCourse());
        return q.getResultList();
    }
}
