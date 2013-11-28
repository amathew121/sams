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
 * @author piit
 */
@Embeddable
public class LectureTagsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lecture")
    private int idLecture;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tag")
    private String tag;

    public LectureTagsPK() {
    }

    public LectureTagsPK(int idLecture, String tag) {
        this.idLecture = idLecture;
        this.tag = tag;
    }

    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLecture;
        hash += (tag != null ? tag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LectureTagsPK)) {
            return false;
        }
        LectureTagsPK other = (LectureTagsPK) object;
        if (this.idLecture != other.idLecture) {
            return false;
        }
        if ((this.tag == null && other.tag != null) || (this.tag != null && !this.tag.equals(other.tag))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LectureTagsPK[ idLecture=" + idLecture + ", tag=" + tag + " ]";
    }
    
}
