/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO Entity for table 'attendance'
 * @author Ashish
 */
@Entity
@Table(name = "attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findByIdAttendance", query = "SELECT a FROM Attendance a WHERE a.idAttendance = :idAttendance"),
    @NamedQuery(name = "Attendance.findByIdLecture", query = "SELECT a FROM Attendance a WHERE a.idLecture = :idLecture")})

public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_attendance")
    private Long idAttendance;
    @JoinColumn(name = "id_lecture", referencedColumnName = "id_lecture")
    @ManyToOne
    private Lecture idLecture;
    @JoinColumn(name = "id_current_student", referencedColumnName = "id_current_student")
    @ManyToOne
    private CurrentStudent idCurrentStudent;

    /**
     * Creates Attendance Entity.
     */
    public Attendance() {
    }

    /**
     * Creates Attendance Entity with the specified 'id_attendance'
     * @param idAttendance
     */
    public Attendance(Long idAttendance) {
        this.idAttendance = idAttendance;
    }

    /**
     * Get id_attendance from Attendance Entity
     * @return
     */
    public Long getIdAttendance() {
        return idAttendance;
    }

    /**
     * Set id_attendance for Attendance Entity 
     * @param idAttendance
     */
    public void setIdAttendance(Long idAttendance) {
        this.idAttendance = idAttendance;
    }

    /**
     * Get id_lecture from Attendance Entity
     * @return
     */
    public Lecture getIdLecture() {
        return idLecture;
    }

    /**
     * Set id_lecture for attendance Entity
     * @param idLecture
     */
    public void setIdLecture(Lecture idLecture) {
        this.idLecture = idLecture;
    }

    /**
     * Get id_current_student from Attendance Entity
     * @return
     */
    public CurrentStudent getIdCurrentStudent() {
        return idCurrentStudent;
    }

    /**
     * Set id_current_student for Attendance Entity
     * @param idCurrentStudent
     */
    public void setIdCurrentStudent(CurrentStudent idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttendance != null ? idAttendance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.idAttendance == null && other.idAttendance != null) || (this.idAttendance != null && !this.idAttendance.equals(other.idAttendance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Attendance[ idAttendance=" + idAttendance + " ]";
    }
    
}
