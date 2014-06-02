/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TODO: FInd usages and delete
 * @author Ashish
 */


@ManagedBean(name="attendanceStateBean")
public class attendanceStateFacade {
    
    @PersistenceContext(name = "persistence/LogicalName",
            unitName = "SamJPAPU")
    
    @Resource
    private javax.transaction.UserTransaction utx;

    /**
     *
     * @param object
     */
    protected void persist(Object object) {
        try {
            Context ctx =
                    (Context) new javax.naming.InitialContext().
                    lookup("java:comp/env");
            utx.begin();
            EntityManager em = (EntityManager) ctx.lookup("persistence/LogicalName");
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(
                    getClass().getName()).log(
                    java.util.logging.Level.SEVERE,
                    "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
