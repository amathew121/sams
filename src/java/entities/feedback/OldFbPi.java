/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.feedback;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ashish Mathew
 */
@Entity
@Table(name = "old_fb_pi", catalog = "piit", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OldFbPi.findAll", query = "SELECT o FROM OldFbPi o"),
    @NamedQuery(name = "OldFbPi.findAllGroupByUser", query = "SELECT o FROM OldFbPi o GROUP BY o.oldFbPiPK.facId"),
    @NamedQuery(name = "OldFbPi.findByFacId", query = "SELECT o FROM OldFbPi o WHERE o.oldFbPiPK.facId = :facId ORDER BY o.oldFbPiPK.subId,o.oldFbPiPK.division,o.oldFbPiPK.batch"),
    @NamedQuery(name = "OldFbPi.findByFname", query = "SELECT o FROM OldFbPi o WHERE o.fname = :fname"),
    @NamedQuery(name = "OldFbPi.findByLname", query = "SELECT o FROM OldFbPi o WHERE o.lname = :lname"),
    @NamedQuery(name = "OldFbPi.findBySubId", query = "SELECT o FROM OldFbPi o WHERE o.oldFbPiPK.subId = :subId"),
    @NamedQuery(name = "OldFbPi.findByFtype", query = "SELECT o FROM OldFbPi o WHERE o.ftype = :ftype"),
    @NamedQuery(name = "OldFbPi.findByDivision", query = "SELECT o FROM OldFbPi o WHERE o.oldFbPiPK.division = :division"),
    @NamedQuery(name = "OldFbPi.findByBatch", query = "SELECT o FROM OldFbPi o WHERE o.oldFbPiPK.batch = :batch"),
    @NamedQuery(name = "OldFbPi.findByPi", query = "SELECT o FROM OldFbPi o WHERE o.pi = :pi")})
public class OldFbPi implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OldFbPiPK oldFbPiPK;
    @Size(max = 100)
    @Column(name = "fname")
    private String fname;
    @Size(max = 100)
    @Column(name = "lname")
    private String lname;
    @Column(name = "ftype")
    private Short ftype;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pi")
    private BigDecimal pi;

    public OldFbPi() {
    }

    public OldFbPi(OldFbPiPK oldFbPiPK) {
        this.oldFbPiPK = oldFbPiPK;
    }

    public OldFbPi(String facId, String subId, String division, short batch) {
        this.oldFbPiPK = new OldFbPiPK(facId, subId, division, batch);
    }

    public OldFbPiPK getOldFbPiPK() {
        return oldFbPiPK;
    }

    public void setOldFbPiPK(OldFbPiPK oldFbPiPK) {
        this.oldFbPiPK = oldFbPiPK;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public short getFtype() {
        return ftype;
    }
    
    public String getFtypeD() {
        if (ftype == 0)
            return "Theory";
        else
            return "Prac/Tut";
    }

    public void setFtype(Short ftype) {
        this.ftype = ftype;
    }

    public BigDecimal getPi() {
        return pi;
    }

    public void setPi(BigDecimal pi) {
        this.pi = pi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oldFbPiPK != null ? oldFbPiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OldFbPi)) {
            return false;
        }
        OldFbPi other = (OldFbPi) object;
        if ((this.oldFbPiPK == null && other.oldFbPiPK != null) || (this.oldFbPiPK != null && !this.oldFbPiPK.equals(other.oldFbPiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OldFbPi[ oldFbPiPK=" + oldFbPiPK + " ]";
    }
    
}
