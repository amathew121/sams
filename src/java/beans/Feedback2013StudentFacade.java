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
 * Enterprise JavaBean for Feedback2013student entity
 * @author piit
 */
@Stateless
public class Feedback2013StudentFacade extends AbstractFacade<Feedback2013Student> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the feedback2013student EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates feedback2013student EJB
     */
    public Feedback2013StudentFacade() {
        super(Feedback2013Student.class);
    }

    /**
     * gets the list of feedback student entity for the class taken by the specified coordinator
     * @param c
     * @return
     */
    public List<Feedback2013Student> getStudentList(Coordinator c) {
        Query q = em.createNamedQuery("Feedback2013Student.findBySemDivBatch");
        q.setParameter("semester", c.getCoordinatorPK().getSemester());
        q.setParameter("division", c.getCoordinatorPK().getDivision());
        q.setParameter("programCourse", c.getProgramCourse());
        return q.getResultList();
    }
}
