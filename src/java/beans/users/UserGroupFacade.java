/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.users;

import beans.AbstractFacade;
import entities.users.UserGroup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Enterprise JavaBean for usergroup entity
 * @author Ashish
 */
@Stateless
public class UserGroupFacade extends AbstractFacade<UserGroup> {
    @PersistenceContext(unitName = "SamJPAPU")
    private EntityManager em;

    /**
     * gets entity manager for usergroup EJB
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * creates usergroup EJB
     */
    public UserGroupFacade() {
        super(UserGroup.class);
    }
    
}
