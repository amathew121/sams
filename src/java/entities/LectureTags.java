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
 *
 * @author piit
 */
@Entity
@Table(name = "lecture_tags")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LectureTags.findAll", query = "SELECT l FROM LectureTags l"),
    @NamedQuery(name = "LectureTags.findByIdLecture", query = "SELECT l FROM LectureTags l WHERE l.lectureTagsPK.idLecture = :idLecture"),
    @NamedQuery(name = "LectureTags.findByTag", query = "SELECT l FROM LectureTags l WHERE l.lectureTagsPK.tag = :tag")})
public class LectureTags implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LectureTagsPK lectureTagsPK;
    @JoinColumn(name = "id_lecture", referencedColumnName = "id_lecture", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lecture lecture;

    public LectureTags() {
    }

    public LectureTags(LectureTagsPK lectureTagsPK) {
        this.lectureTagsPK = lectureTagsPK;
    }

    public LectureTags(int idLecture, String tag) {
        this.lectureTagsPK = new LectureTagsPK(idLecture, tag);
    }

    public LectureTagsPK getLectureTagsPK() {
        return lectureTagsPK;
    }

    public void setLectureTagsPK(LectureTagsPK lectureTagsPK) {
        this.lectureTagsPK = lectureTagsPK;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lectureTagsPK != null ? lectureTagsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LectureTags)) {
            return false;
        }
        LectureTags other = (LectureTags) object;
        if ((this.lectureTagsPK == null && other.lectureTagsPK != null) || (this.lectureTagsPK != null && !this.lectureTagsPK.equals(other.lectureTagsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return lectureTagsPK.getTag();
    }
    
}
