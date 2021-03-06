/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.feedback;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'old_faculty_comments'
 * @author piit
 */
@Entity
@Table(name = "old_faculty_comments", catalog = "piit", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OldFacultyComments.findAll", query = "SELECT o FROM OldFacultyComments o"),
    @NamedQuery(name = "OldFacultyComments.findById", query = "SELECT o FROM OldFacultyComments o WHERE o.id = :id"),
    @NamedQuery(name = "OldFacultyComments.findByFacId", query = "SELECT o FROM OldFacultyComments o WHERE o.facId = :facId"),
    @NamedQuery(name = "OldFacultyComments.findByFS", query = "SELECT o FROM OldFacultyComments o WHERE o.facId = :facId AND o.subId = :subId AND o.division = :division AND o.ftype = :ftype AND o.batch = :batch"),
    @NamedQuery(name = "OldFacultyComments.findByFST", query = "SELECT o FROM OldFacultyComments o WHERE o.facId = :facId AND o.subId = :subId AND o.division = :division AND o.ftype = :ftype"),
    @NamedQuery(name = "OldFacultyComments.findByFacName", query = "SELECT o FROM OldFacultyComments o WHERE o.facName = :facName"),
    @NamedQuery(name = "OldFacultyComments.findBySubId", query = "SELECT o FROM OldFacultyComments o WHERE o.subId = :subId"),
    @NamedQuery(name = "OldFacultyComments.findByDivision", query = "SELECT o FROM OldFacultyComments o WHERE o.division = :division"),
    @NamedQuery(name = "OldFacultyComments.findByFtype", query = "SELECT o FROM OldFacultyComments o WHERE o.ftype = :ftype"),
    @NamedQuery(name = "OldFacultyComments.findByBatch", query = "SELECT o FROM OldFacultyComments o WHERE o.batch = :batch"),
    @NamedQuery(name = "OldFacultyComments.findByShowStatus", query = "SELECT o FROM OldFacultyComments o WHERE o.showStatus = :showStatus")})
public class OldFacultyComments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "fac_id")
    private String facId;
    @Size(max = 100)
    @Column(name = "fac_name")
    private String facName;
    @Size(max = 200)
    @Column(name = "sub_id")
    private String subId;
    @Size(max = 1)
    @Column(name = "division")
    private String division;
    @Column(name = "ftype")
    private Short ftype;
    @Column(name = "batch")
    private Short batch;
    @Lob
    @Size(max = 65535)
    @Column(name = "comments")
    private String comments;
    @Size(max = 100)
    @Column(name = "show_status")
    private String showStatus;

    /**
     * Creates OldFacultyComments Entity
     */
    public OldFacultyComments() {
    }

    /**
     * Creates OldFacultyComments Entity with the specified id
     * @param id
     */
    public OldFacultyComments(Integer id) {
        this.id = id;
    }

    /**
     * Get id from OldFacultyComments Entity
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set id for OldFacultyComments Entity
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get fac_id from OldFacultyComments Entity
     * @return
     */
    public String getFacId() {
        return facId;
    }

    /**
     * Set fac_id for OldFacultyComments Entity
     * @param facId
     */
    public void setFacId(String facId) {
        this.facId = facId;
    }

    /**
     * Get fac_name from OldFacultyComments Entity
     * @return
     */
    public String getFacName() {
        return facName;
    }

    /**
     * Set fac_name for OldFacultyComments Entity
     * @param facName
     */
    public void setFacName(String facName) {
        this.facName = facName;
    }

    /**
     * Get sub_id from OldFacultyComments Entity
     * @return
     */
    public String getSubId() {
        return subId;
    }

    /**
     * Set sub_id for OldFacultyComments Entity
     * @param subId
     */
    public void setSubId(String subId) {
        this.subId = subId;
    }

    /**
     * Get division from OldFacultyComments Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for OldFacultyComments Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get ftype from OldFacultyComments Entity
     * @return
     */
    public Short getFtype() {
        return ftype;
    }

    /**
     * Set ftype for OldFacultyComments Entity
     * @param ftype
     */
    public void setFtype(Short ftype) {
        this.ftype = ftype;
    }

    /**
     * Get batch from OldFacultyComments Entity
     * @return
     */
    public Short getBatch() {
        return batch;
    }

    /**
     * Set batch for OldFacultyComments Entity
     * @param batch
     */
    public void setBatch(Short batch) {
        this.batch = batch;
    }

    /**
     * Get comments from OldFacultyComments Entity
     * @return
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set comments for OldFacultyComments Entity
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Get show_status from OldFacultyComments Entity
     * @return
     */
    public String getShowStatus() {
        return showStatus;
    }

    /**
     * Set show_status for OldFacultyComments Entity
     * @param showStatus
     */
    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OldFacultyComments)) {
            return false;
        }
        OldFacultyComments other = (OldFacultyComments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OldFacultyComments[ id=" + id + " ]";
    }
    
}
