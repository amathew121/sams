/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Lecture;
import entities.LectureTags;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Enterprise JavaBean for lecturetags entity
 * @author piit
 */
@Stateless
public class LectureTagsFacade extends AbstractFacade<LectureTags> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * Gets Entity Manager for the lecturetags EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates lecture EJB
     */
    public LectureTagsFacade() {
        super(LectureTags.class);
    }
    
    /**
     * get the lecture tags for the specified lecture
     * @param lecture
     * @return
     */
    public List<LectureTags> getTags(Lecture lecture) {
        Query q = em.createNamedQuery("LectureTags.findByIdLecture");
        q.setParameter("idLecture", lecture.getIdLecture());
        return q.getResultList();
    }
}
