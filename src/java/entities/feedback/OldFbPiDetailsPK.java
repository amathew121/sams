/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.feedback;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ashish Mathew
 */
@Embeddable
public class OldFbPiDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "fac_id")
    private String facId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "sub_id")
    private String subId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "course_id")
    private String courseId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "division")
    private String division;
    @Basic(optional = false)
    @NotNull
    @Column(name = "batch")
    private short batch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qno")
    private short qno;

    /**
     * Creates OldFbPiDetailsPK Entity
     */
    public OldFbPiDetailsPK() {
    }

    /**
     * Creates OldFbPiDetailsPK Entity with the specified fac_id, sub_id, course_id, division, batch and qno
     * @param facId
     * @param subId
     * @param courseId
     * @param division
     * @param batch
     * @param qno
     */
    public OldFbPiDetailsPK(String facId, String subId, String courseId, String division, short batch, short qno) {
        this.facId = facId;
        this.subId = subId;
        this.courseId = courseId;
        this.division = division;
        this.batch = batch;
        this.qno = qno;
    }

    /**
     * Get fac_id from OldFbPiDetailsPK Entity
     * @return
     */
    public String getFacId() {
        return facId;
    }

    /**
     * Set fac_id for OldFbPiDetailsPK Entity
     * @param facId
     */
    public void setFacId(String facId) {
        this.facId = facId;
    }

    /**
     * Get sub_id from OldFbPiDetailsPK Entity
     * @return
     */
    public String getSubId() {
        return subId;
    }

    /**
     * Set sub_id for OldFbPiDetailsPK Entity
     * @param subId
     */
    public void setSubId(String subId) {
        this.subId = subId;
    }

    /**
     * Get course_id from OldFbPiDetailsPK Entity
     * @return
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Set course_id for OldFbPiDetailsPK Entity
     * @param courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Get division from OldFbPiDetailsPK Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for OldFbPiDetailsPK Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get batch from OldFbPiDetailsPK Entity
     * @return
     */
    public short getBatch() {
        return batch;
    }

    /**
     * Set batch for OldFbPiDetailsPK Entity
     * @param batch
     */
    public void setBatch(short batch) {
        this.batch = batch;
    }

    /**
     * Get qno from OldFbPiDetailsPK Entity
     * @return
     */
    public short getQno() {
        return qno;
    }

    /**
     * Set qno for OldFbPiDetailsPK Entity
     * @param qno
     */
    public void setQno(short qno) {
        this.qno = qno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facId != null ? facId.hashCode() : 0);
        hash += (subId != null ? subId.hashCode() : 0);
        hash += (courseId != null ? courseId.hashCode() : 0);
        hash += (division != null ? division.hashCode() : 0);
        hash += (int) batch;
        hash += (int) qno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OldFbPiDetailsPK)) {
            return false;
        }
        OldFbPiDetailsPK other = (OldFbPiDetailsPK) object;
        if ((this.facId == null && other.facId != null) || (this.facId != null && !this.facId.equals(other.facId))) {
            return false;
        }
        if ((this.subId == null && other.subId != null) || (this.subId != null && !this.subId.equals(other.subId))) {
            return false;
        }
        if ((this.courseId == null && other.courseId != null) || (this.courseId != null && !this.courseId.equals(other.courseId))) {
            return false;
        }
        if ((this.division == null && other.division != null) || (this.division != null && !this.division.equals(other.division))) {
            return false;
        }
        if (this.batch != other.batch) {
            return false;
        }
        if (this.qno != other.qno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OldFbPiDetailsPK[ facId=" + facId + ", subId=" + subId + ", courseId=" + courseId + ", division=" + division + ", batch=" + batch + ", qno=" + qno + " ]";
    }
    
}
