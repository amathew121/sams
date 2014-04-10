/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'user_group'
 * @author Ashish
 */
@Entity
@Table(name = "user_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u"),
    @NamedQuery(name = "UserGroup.findByUserName", query = "SELECT u FROM UserGroup u WHERE u.userGroupPK.userName = :userName"),
    @NamedQuery(name = "UserGroup.findByRoleName", query = "SELECT u FROM UserGroup u WHERE u.userGroupPK.roleName = :roleName")})
public class UserGroup implements Serializable {
    @JoinColumn(name = "user_name", referencedColumnName = "id_faculty", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Faculty faculty;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserGroupPK userGroupPK;

    /**
     * Creates UserGroup Entity 
     */
    public UserGroup() {
    }

    /**
     * Creates UserGroup Entity with the specified userGroupPK 
     * @param userGroupPK
     */
    public UserGroup(UserGroupPK userGroupPK) {
        this.userGroupPK = userGroupPK;
    }

    /**
     * Creates UserGroup Entity with the specified user_name and role_name
     * @param userName
     * @param roleName
     */
    public UserGroup(String userName, String roleName) {
        this.userGroupPK = new UserGroupPK(userName, roleName);
    }

    /**
     * Get UserGroupPK from the UserGroup Entity
     * @return
     */
    public UserGroupPK getUserGroupPK() {
        return userGroupPK;
    }

    /**
     * Set UserGroupPK for the UserGroup Entity
     * @param userGroupPK
     */
    public void setUserGroupPK(UserGroupPK userGroupPK) {
        this.userGroupPK = userGroupPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userGroupPK != null ? userGroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.userGroupPK == null && other.userGroupPK != null) || (this.userGroupPK != null && !this.userGroupPK.equals(other.userGroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserGroup[ userGroupPK=" + userGroupPK + " ]";
    }

    /**
     * 
     * @return
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     *
     * @param faculty
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
}
