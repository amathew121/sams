/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.FacultySubjectView;
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
    
    public List <FacultySubjectView> getFSViewById(String s)
    {
        Query q = em.createNamedQuery("FacultySubjectView.findByIdFaculty");
        q.setParameter("idFaculty", s);
        List <FacultySubjectView> l = q.getResultList();
        count = l.size();
        return l;
        
        
    }
    
}
