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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ashish
 */
@Entity
@Table(name = "teaching_plan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeachingPlan.findAll", query = "SELECT t FROM TeachingPlan t"),
    @NamedQuery(name = "TeachingPlan.findByIdTeachingPlan", query = "SELECT t FROM TeachingPlan t WHERE t.idTeachingPlan = :idTeachingPlan"),
    @NamedQuery(name = "TeachingPlan.findByIdFacultySubject", query = "SELECT t FROM TeachingPlan t WHERE t.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "TeachingPlan.findByPlannedDate", query = "SELECT t FROM TeachingPlan t WHERE t.plannedDate = :plannedDate"),
    @NamedQuery(name = "TeachingPlan.findByActualDate", query = "SELECT t FROM TeachingPlan t WHERE t.actualDate = :actualDate"),
    @NamedQuery(name = "TeachingPlan.findByLectureNo", query = "SELECT t FROM TeachingPlan t WHERE t.lectureNo = :lectureNo")})
public class TeachingPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_teaching_plan")
    private Integer idTeachingPlan;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "topics_planned")
    private String topicsPlanned;
    @Column(name = "planned_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plannedDate;
    @Column(name = "actual_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lecture_no")
    private short lectureNo;
    @JoinColumn(name = "id_faculty_subject", referencedColumnName = "id_faculty_subject")
    @ManyToOne(optional = false)
    private FacultySubject idFacultySubject;

    public TeachingPlan() {
    }

    public TeachingPlan(Integer idTeachingPlan) {
        this.idTeachingPlan = idTeachingPlan;
    }

    public TeachingPlan(Integer idTeachingPlan, String topicsPlanned, short lectureNo) {
        this.idTeachingPlan = idTeachingPlan;
        this.topicsPlanned = topicsPlanned;
        this.lectureNo = lectureNo;
    }

    public Integer getIdTeachingPlan() {
        return idTeachingPlan;
    }

    public void setIdTeachingPlan(Integer idTeachingPlan) {
        this.idTeachingPlan = idTeachingPlan;
    }

    public String getTopicsPlanned() {
        return topicsPlanned;
    }

    public void setTopicsPlanned(String topicsPlanned) {
        this.topicsPlanned = topicsPlanned;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public short getLectureNo() {
        return lectureNo;
    }

    public void setLectureNo(short lectureNo) {
        this.lectureNo = lectureNo;
    }

    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(FacultySubject idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTeachingPlan != null ? idTeachingPlan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeachingPlan)) {
            return false;
        }
        TeachingPlan other = (TeachingPlan) object;
        if ((this.idTeachingPlan == null && other.idTeachingPlan != null) || (this.idTeachingPlan != null && !this.idTeachingPlan.equals(other.idTeachingPlan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TeachingPlan[ idTeachingPlan=" + idTeachingPlan + " ]";
    }
    
}
