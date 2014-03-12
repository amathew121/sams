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
 *
 * @author phcoe
 */
@Stateless
public class LectureTagsFacade extends AbstractFacade<LectureTags> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LectureTagsFacade() {
        super(LectureTags.class);
    }
    
    public List<LectureTags> getTags(Lecture lecture) {
        Query q = em.createNamedQuery("LectureTags.findByIdLecture");
        q.setParameter("idLecture", lecture.getIdLecture());
        return q.getResultList();
    }
}
