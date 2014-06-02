/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty.lecture;

import entities.subject.faculty.FacultySubject;
import java.io.Serializable;
import java.util.Calendar;
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
 * Creates POJO Entity for table 'teaching_plan'
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
    @NamedQuery(name = "TeachingPlan.findByLectureNo", query = "SELECT t FROM TeachingPlan t WHERE t.lectureNo = :lectureNo")})
public class TeachingPlan implements Serializable {
    @Column(name = "module_no")
    private Short moduleNo;
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
   @Basic(optional = false)
    @NotNull
    @Column(name = "lecture_no")
    private short lectureNo;
    @JoinColumn(name = "id_faculty_subject", referencedColumnName = "id_faculty_subject")
    @ManyToOne(optional = false)
    private FacultySubject idFacultySubject;

    /**
     * Create TeachingPlan Entity
     */
    public TeachingPlan() {
    }

    /**
     * Create TeachingPlan Entity with the specified 'id_teaching_plan'
     * @param idTeachingPlan
     */
    public TeachingPlan(Integer idTeachingPlan) {
        this.idTeachingPlan = idTeachingPlan;
    }

    /**
     * Create TeachingPlan Entity with the specified id_teaching_plan,topics_planned and lecture_no
     * @param idTeachingPlan
     * @param topicsPlanned
     * @param lectureNo
     */
    public TeachingPlan(Integer idTeachingPlan, String topicsPlanned, short lectureNo) {
        this.idTeachingPlan = idTeachingPlan;
        this.topicsPlanned = topicsPlanned;
        this.lectureNo = lectureNo;
    }

    /**
     * Get id_teaching_plan from TeachingPlan Entity
     * @return
     */
    public Integer getIdTeachingPlan() {
        return idTeachingPlan;
    }

    /**
     * Set id_teaching_plan for TeachingPlan Entity
     * @param idTeachingPlan
     */
    public void setIdTeachingPlan(Integer idTeachingPlan) {
        this.idTeachingPlan = idTeachingPlan;
    }

    /**
     * Get topics_planned from TeachingPlan Entity
     * @return
     */
    public String getTopicsPlanned() {
        return topicsPlanned;
    }

    /**
     * Set topics_planned for TeachingPlan Entity
     * @param topicsPlanned
     */
    public void setTopicsPlanned(String topicsPlanned) {
        this.topicsPlanned = topicsPlanned;
    }

    /**
     * Get planned_date from TeachingPlan Entity
     * @return
     */
    public Date getPlannedDate() {
        return plannedDate;
    }

    /**
     * Set planned_date for TeachingPlan Entity
     * @param plannedDate
     */
    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    /**
     * Get lecture_no from TeachingPlan Entity
     * @return
     */
    public short getLectureNo() {
        return lectureNo;
    }

    /**
     * Set lecture_no for TeachingPlan Entity
     * @param lectureNo
     */
    public void setLectureNo(short lectureNo) {
        this.lectureNo = lectureNo;
    }

    /**
     * Get id_faculty_subject from TeachingPlan Entity
     * @return
     */
    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject for TeachingPlan Entity
     * @param idFacultySubject
     */
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

    /**
     *
     * @return
     */
    public Short getModuleNo() {
        return moduleNo;
    }

    /**
     *
     * @param moduleNo
     */
    public void setModuleNo(Short moduleNo) {
        this.moduleNo = moduleNo;
    }
    
}
