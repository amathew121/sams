/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ashish
 */
@Entity
@Table(name = "lecture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lecture.findAll", query = "SELECT l FROM Lecture l"),
    @NamedQuery(name = "Lecture.findByIdLecture", query = "SELECT l FROM Lecture l WHERE l.idLecture = :idLecture"),
    @NamedQuery(name = "Lecture.findByIdFacultySubject", query = "SELECT l FROM Lecture l WHERE l.idFacultySubject = :idFacultySubject ORDER BY l.lectureDate,l.lectureStartTime"),
    @NamedQuery(name = "Lecture.findByLectureDate", query = "SELECT l FROM Lecture l WHERE l.lectureDate = :lectureDate"),
    @NamedQuery(name = "Lecture.findByLectureStartTime", query = "SELECT l FROM Lecture l WHERE l.lectureStartTime = :lectureStartTime")})
public class Lecture implements Serializable {
    @Lob
    @Size(max = 16777215)
    @Column(name = "content_delivered")
    private String contentDelivered;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lecture")
    private Integer idLecture;
    @Column(name = "lecture_date")
    @Temporal(TemporalType.DATE)
    private Date lectureDate;
    @Column(name = "lecture_start_time")
    @Temporal(TemporalType.TIME)
    private Date lectureStartTime;
    @JoinColumn(name = "id_faculty_subject", referencedColumnName = "id_faculty_subject")
    @ManyToOne
    private FacultySubject idFacultySubject;
    @OneToOne(mappedBy = "idLecture")
    private Attendance attendance;

    @Transient
    private Long attendanceCount;

    public Long getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(Long attendanceCount) {
        this.attendanceCount = attendanceCount;
    }
    
    public Lecture() {
    }

    public Lecture(Integer idLecture) {
        this.idLecture = idLecture;
    }

    public Integer getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(Integer idLecture) {
        this.idLecture = idLecture;
    }

    public Date getLectureDate() {
        return lectureDate;
    }

    public void setLectureDate(Date lectureDate) {
        this.lectureDate = lectureDate;
    }

    public Date getLectureStartTime() {
        return lectureStartTime;
    }

    public void setLectureStartTime(Date lectureStartTime) {
        this.lectureStartTime = lectureStartTime;
    }

    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(FacultySubject idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLecture != null ? idLecture.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lecture)) {
            return false;
        }
        Lecture other = (Lecture) object;
        if ((this.idLecture == null && other.idLecture != null) || (this.idLecture != null && !this.idLecture.equals(other.idLecture))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lecture[ idLecture=" + idLecture + " ]";
    }

    public String getContentDelivered() {
        return contentDelivered;
    }

    public void setContentDelivered(String contentDelivered) {
        this.contentDelivered = contentDelivered;
    }


}
