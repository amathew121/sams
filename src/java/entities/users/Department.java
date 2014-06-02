/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.users;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creates POJO entity for table 'department'
 * @author Ashish
 */
@Entity
@Table(name = "department")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d"),
    @NamedQuery(name = "Department.findByIdDepartment", query = "SELECT d FROM Department d WHERE d.idDepartment = :idDepartment"),
    @NamedQuery(name = "Department.findByDepartmentName", query = "SELECT d FROM Department d WHERE d.departmentName = :departmentName")})
public class Department implements Serializable {
    @JoinTable(name = "hod", joinColumns = {
        @JoinColumn(name = "id_department", referencedColumnName = "id_department")}, inverseJoinColumns = {
        @JoinColumn(name = "id_faculty", referencedColumnName = "id_faculty")})
    @ManyToMany
    private Collection<Faculty> facultyCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_department")
    private String idDepartment;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "department_name")
    private String departmentName;

    /**
     * Creates Department Entity
     */
    public Department() {
    }

    /**
     * Creates Department Entity with the specified 'id_department'
     * @param idDepartment
     */
    public Department(String idDepartment) {
        this.idDepartment = idDepartment;
    }

    /**
     * Creates Department Entity with the specified 'id_department' and 'department_name'
     * @param idDepartment
     * @param departmentName
     */
    public Department(String idDepartment, String departmentName) {
        this.idDepartment = idDepartment;
        this.departmentName = departmentName;
    }

    /**
     * Get id_department from Department Entity
     * @return
     */
    public String getIdDepartment() {
        return idDepartment;
    }

    /**
     * Set id_department for Department Entity
     * @param idDepartment
     */
    public void setIdDepartment(String idDepartment) {
        this.idDepartment = idDepartment;
    }

    /**
     * Get department_name from Department Entity
     * @return
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Set department_name for Department Entity
     * @param departmentName
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepartment != null ? idDepartment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.idDepartment == null && other.idDepartment != null) || (this.idDepartment != null && !this.idDepartment.equals(other.idDepartment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return departmentName;
    }

    /**
     * Gets collection of Faculty Entities for the Department Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<Faculty> getFacultyCollection() {
        return facultyCollection;
    }

    /**
     * Sets collection of Faculty Entities for the Department Entity as a foreign key
     * @param facultyCollection
     */
    public void setFacultyCollection(Collection<Faculty> facultyCollection) {
        this.facultyCollection = facultyCollection;
    }
    
}
