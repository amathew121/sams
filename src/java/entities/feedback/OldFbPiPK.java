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
public class OldFbPiPK implements Serializable {
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
    @Size(min = 1, max = 1)
    @Column(name = "division")
    private String division;
    @Basic(optional = false)
    @NotNull
    @Column(name = "batch")
    private short batch;

    /**
     * Creates OldFbPiPK Entity
     */
    public OldFbPiPK() {
    }

    /**
     * Creates OldFbPiPK Entity with the specified faculty_id, subject_id, division and batch
     * @param facId
     * @param subId
     * @param division
     * @param batch
     */
    public OldFbPiPK(String facId, String subId, String division, short batch) {
        this.facId = facId;
        this.subId = subId;
        this.division = division;
        this.batch = batch;
    }

    /**
     * Get fac_id from OldFbPiPK Entity
     * @return
     */
    public String getFacId() {
        return facId;
    }

    /**
     * Set fac_id for OldFbPiPK Entity
     * @param facId
     */
    public void setFacId(String facId) {
        this.facId = facId;
    }

    /**
     * Get sub_id from OldFbPiPK Entity
     * @return
     */
    public String getSubId() {
        return subId;
    }

    /**
     * Set sub_id for OldFbPiPK Entity
     * @param subId
     */
    public void setSubId(String subId) {
        this.subId = subId;
    }

    /**
     * Get division from OldFbPiPK Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for OldFbPiPK Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get batch from OldFbPiPK Entity
     * @return
     */
    public short getBatch() {
        return batch;
    }

    /**
     * Set batch for OldFbPiPK Entity
     * @param batch
     */
    public void setBatch(short batch) {
        this.batch = batch;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facId != null ? facId.hashCode() : 0);
        hash += (subId != null ? subId.hashCode() : 0);
        hash += (division != null ? division.hashCode() : 0);
        hash += (int) batch;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OldFbPiPK)) {
            return false;
        }
        OldFbPiPK other = (OldFbPiPK) object;
        if ((this.facId == null && other.facId != null) || (this.facId != null && !this.facId.equals(other.facId))) {
            return false;
        }
        if ((this.subId == null && other.subId != null) || (this.subId != null && !this.subId.equals(other.subId))) {
            return false;
        }
        if ((this.division == null && other.division != null) || (this.division != null && !this.division.equals(other.division))) {
            return false;
        }
        if (this.batch != other.batch) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OldFbPiPK[ facId=" + facId + ", subId=" + subId + ", division=" + division + ", batch=" + batch + " ]";
    }
    
}
