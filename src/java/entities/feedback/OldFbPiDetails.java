/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.feedback;

import java.io.Serializable;
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
@Table(name = "old_fb_pi_details", catalog = "piit", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OldFbPiDetails.findAll", query = "SELECT o FROM OldFbPiDetails o"),
    @NamedQuery(name = "OldFbPiDetails.findByFacId", query = "SELECT o FROM OldFbPiDetails o WHERE o.oldFbPiDetailsPK.facId = :facId"),
    @NamedQuery(name = "OldFbPiDetails.findByFS", query = "SELECT o FROM OldFbPiDetails o WHERE o.oldFbPiDetailsPK.facId = :facId AND o.oldFbPiDetailsPK.subId = :subId AND o.oldFbPiDetailsPK.division = :division AND o.oldFbPiDetailsPK.batch = :batch AND o.ftype = :ftype"), 
    @NamedQuery(name = "OldFbPiDetails.findByFname", query = "SELECT o FROM OldFbPiDetails o WHERE o.fname = :fname"),
    @NamedQuery(name = "OldFbPiDetails.findByLname", query = "SELECT o FROM OldFbPiDetails o WHERE o.lname = :lname"),
    @NamedQuery(name = "OldFbPiDetails.findBySubId", query = "SELECT o FROM OldFbPiDetails o WHERE o.oldFbPiDetailsPK.subId = :subId"),
    @NamedQuery(name = "OldFbPiDetails.findByCourseId", query = "SELECT o FROM OldFbPiDetails o WHERE o.oldFbPiDetailsPK.courseId = :courseId"),
    @NamedQuery(name = "OldFbPiDetails.findByFtype", query = "SELECT o FROM OldFbPiDetails o WHERE o.ftype = :ftype"),
    @NamedQuery(name = "OldFbPiDetails.findByDivision", query = "SELECT o FROM OldFbPiDetails o WHERE o.oldFbPiDetailsPK.division = :division"),
    @NamedQuery(name = "OldFbPiDetails.findByBatch", query = "SELECT o FROM OldFbPiDetails o WHERE o.oldFbPiDetailsPK.batch = :batch"),
    @NamedQuery(name = "OldFbPiDetails.findByQno", query = "SELECT o FROM OldFbPiDetails o WHERE o.oldFbPiDetailsPK.qno = :qno"),
    @NamedQuery(name = "OldFbPiDetails.findByQtext", query = "SELECT o FROM OldFbPiDetails o WHERE o.qtext = :qtext"),
    @NamedQuery(name = "OldFbPiDetails.findByTa5", query = "SELECT o FROM OldFbPiDetails o WHERE o.ta5 = :ta5"),
    @NamedQuery(name = "OldFbPiDetails.findByTa4", query = "SELECT o FROM OldFbPiDetails o WHERE o.ta4 = :ta4"),
    @NamedQuery(name = "OldFbPiDetails.findByTa3", query = "SELECT o FROM OldFbPiDetails o WHERE o.ta3 = :ta3"),
    @NamedQuery(name = "OldFbPiDetails.findByTa2", query = "SELECT o FROM OldFbPiDetails o WHERE o.ta2 = :ta2"),
    @NamedQuery(name = "OldFbPiDetails.findByTa1", query = "SELECT o FROM OldFbPiDetails o WHERE o.ta1 = :ta1")})
public class OldFbPiDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OldFbPiDetailsPK oldFbPiDetailsPK;
    @Size(max = 100)
    @Column(name = "fname")
    private String fname;
    @Size(max = 100)
    @Column(name = "lname")
    private String lname;
    @Column(name = "ftype")
    private Short ftype;
    @Size(max = 500)
    @Column(name = "qtext")
    private String qtext;
    @Column(name = "ta5")
    private Integer ta5;
    @Column(name = "ta4")
    private Integer ta4;
    @Column(name = "ta3")
    private Integer ta3;
    @Column(name = "ta2")
    private Integer ta2;
    @Column(name = "ta1")
    private Integer ta1;

    public OldFbPiDetails() {
    }

    public OldFbPiDetails(OldFbPiDetailsPK oldFbPiDetailsPK) {
        this.oldFbPiDetailsPK = oldFbPiDetailsPK;
    }

    public OldFbPiDetails(String facId, String subId, String courseId, String division, short batch, short qno) {
        this.oldFbPiDetailsPK = new OldFbPiDetailsPK(facId, subId, courseId, division, batch, qno);
    }

    public OldFbPiDetailsPK getOldFbPiDetailsPK() {
        return oldFbPiDetailsPK;
    }

    public void setOldFbPiDetailsPK(OldFbPiDetailsPK oldFbPiDetailsPK) {
        this.oldFbPiDetailsPK = oldFbPiDetailsPK;
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

    public Short getFtype() {
        return ftype;
    }

    public void setFtype(Short ftype) {
        this.ftype = ftype;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String qtext) {
        this.qtext = qtext;
    }

    public Integer getTa5() {
        return ta5;
    }

    public void setTa5(Integer ta5) {
        this.ta5 = ta5;
    }

    public Integer getTa4() {
        return ta4;
    }

    public void setTa4(Integer ta4) {
        this.ta4 = ta4;
    }

    public Integer getTa3() {
        return ta3;
    }

    public void setTa3(Integer ta3) {
        this.ta3 = ta3;
    }

    public Integer getTa2() {
        return ta2;
    }

    public void setTa2(Integer ta2) {
        this.ta2 = ta2;
    }

    public Integer getTa1() {
        return ta1;
    }

    public void setTa1(Integer ta1) {
        this.ta1 = ta1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oldFbPiDetailsPK != null ? oldFbPiDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OldFbPiDetails)) {
            return false;
        }
        OldFbPiDetails other = (OldFbPiDetails) object;
        if ((this.oldFbPiDetailsPK == null && other.oldFbPiDetailsPK != null) || (this.oldFbPiDetailsPK != null && !this.oldFbPiDetailsPK.equals(other.oldFbPiDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OldFbPiDetails[ oldFbPiDetailsPK=" + oldFbPiDetailsPK + " ]";
    }
    
}
