/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author phcoe
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

    public OldFbPiPK() {
    }

    public OldFbPiPK(String facId, String subId, String division, short batch) {
        this.facId = facId;
        this.subId = subId;
        this.division = division;
        this.batch = batch;
    }

    public String getFacId() {
        return facId;
    }

    public void setFacId(String facId) {
        this.facId = facId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public short getBatch() {
        return batch;
    }

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
