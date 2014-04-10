/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import controllers.CurrentStudentController;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creates POJO Entity for table 'lecture'
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
    @NamedQuery(name = "Lecture.findByLectureDateRange", query = "SELECT l FROM Lecture l WHERE l.idFacultySubject = :idFacultySubject AND l.lectureDate >= :startDate AND l.lectureDate <= :endDate ORDER BY l.lectureDate,l.lectureStartTime"),
    @NamedQuery(name = "Lecture.findByLectureDateRangeStart", query = "SELECT l FROM Lecture l WHERE l.idFacultySubject = :idFacultySubject AND l.lectureDate >= :startDate ORDER BY l.lectureDate,l.lectureStartTime"),
    @NamedQuery(name = "Lecture.findByLectureStartTime", query = "SELECT l FROM Lecture l WHERE l.lectureStartTime = :lectureStartTime")})
public class Lecture implements Serializable {
    @Lob
    @Size(max = 16777215)
    @Column(name = "content_beyond_syllabus")
    private String contentBeyondSyllabus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lecture")
    private List<LectureTags> lectureTagsList;
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
    @Transient
    private Boolean check;
    
    @Transient
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();

    @Transient
    private boolean selectAll;

    /**
     * Get check from Lecture Entity
     * @return
     */
    public Boolean getCheck() {
        return check;
    }

    /**
     * Set check for Lecture Entity
     * @param check
     */
    public void setCheck(Boolean check) {
        this.check = check;
    }
    
    /**
     * TODO:
     * @return
     */
    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    /**
     * TODO:
     * @param checked
     */
    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }

    /**
     * TODO:
     * @return
     */
    public boolean isSelectAll() {
        return selectAll;
    }

    /**
     * TODO:
     * @param selectAll
     */
    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    /**
     * TODO:
     * @param event
     */
    public void checkedControl(ValueChangeEvent event) {
        UIData data = (UIData) event.getComponent().findComponent("listComponents");
        CurrentStudent cs = (CurrentStudent) data.getRowData();
        getChecked().put(cs.getIdCurrentStudent(), true);
    }
    
    /**
     * TODO:
     * @param event
     */
    public void selectAllComponents(ValueChangeEvent event) {
        if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {
            if (selectAll) {
                changeMap(checked, true);
                setSelectAll(true);
            } else // If the button is unchecked, unselect all the checkboxes
            {
                changeMap(checked, false);
                setSelectAll(false);
            }
        }
    }

    /**
     * TODO:
     * @param selectedComponentMap
     * @param blnValue
     */
    public void changeMap(Map<Integer, Boolean> selectedComponentMap, Boolean blnValue) {
        if (selectedComponentMap != null) {
            /* Iterator<Integer> itr = selectedComponentMap.keySet().iterator();
             selectedComponentMap.put(attendanceByDiv.get(0).getIdCurrentStudent(),true);
             while (itr.hasNext()) {
             selectedComponentMap.put(itr.next(), true);
             } */
            FacesContext context = FacesContext.getCurrentInstance();
            CurrentStudentController csc = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
            List<CurrentStudent> attendanceByDiv = csc.getAttendanceByDiv(idFacultySubject);
            for (CurrentStudent item : attendanceByDiv) {
                selectedComponentMap.put(item.getIdCurrentStudent(), blnValue);
            }
            setChecked(selectedComponentMap);
        }
    }
    
    /**
     * Get attendance_count from Lecture Entity
     * @return
     */
    public Long getAttendanceCount() {
        return attendanceCount;
    }

    /**
     * Set attendance_count for Lecture Entity
     * @param attendanceCount
     */
    public void setAttendanceCount(Long attendanceCount) {
        this.attendanceCount = attendanceCount;
    }
    
    /**
     * Creates Lecture Entity
     */
    public Lecture() {
    }

    /**
     * Creates Lecture Entity with the specified id_lecture
     * @param idLecture
     */
    public Lecture(Integer idLecture) {
        this.idLecture = idLecture;
    }

    /**
     * Get id_lecture from Lecture entity
     * @return
     */
    public Integer getIdLecture() {
        return idLecture;
    }

    /**
     * Set id_lecture for Lecture entity
     * @param idLecture
     */
    public void setIdLecture(Integer idLecture) {
        this.idLecture = idLecture;
    }

    /**
     * Get lecture_date from Lecture Entity
     * @return
     */
    public Date getLectureDate() {
        return lectureDate;
    }

    /**
     * Set lecture_date for Lecture Entity
     * @param lectureDate
     */
    public void setLectureDate(Date lectureDate) {
        this.lectureDate = lectureDate;
    }

    /**
     * Get lecture_start_time from Lecture Entity
     * @return
     */
    public Date getLectureStartTime() {
        return lectureStartTime;
    }

    /**
     * Set lecture_start_time for Lecture Entity
     * @param lectureStartTime
     */
    public void setLectureStartTime(Date lectureStartTime) {
        this.lectureStartTime = lectureStartTime;
    }

    /**
     * Get id_faculty_subject from Lecture Entity
     * @return
     */
    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject for Lecture Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(FacultySubject idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    /**
     * Get attendance from Lecture Entity
     * @return
     */
    public Attendance getAttendance() {
        return attendance;
    }

    /**
     * Set attendance for Lecture Entity
     * @param attendance
     */
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

    /**
     *
     * @return
     */
    public String getContentDelivered() {
        return contentDelivered;
    }

    /**
     *
     * @param contentDelivered
     */
    public void setContentDelivered(String contentDelivered) {
        this.contentDelivered = contentDelivered;
    }

    /**
     * Gets a list of LectureTags Entities for the Lecture Entity as a foreign key 
     * @return
     */
    @XmlTransient
    public List<LectureTags> getLectureTagsList() {
        return lectureTagsList;
    }

    /**
     * Sets a list of LectureTags Entities for the Lecture Entity as a foreign key
     * @param lectureTagsList
     */
    public void setLectureTagsList(List<LectureTags> lectureTagsList) {
        this.lectureTagsList = lectureTagsList;
    }

    /**
     * Get content_beyond_syllabus from Lecture Entity
     * @return
     */
    public String getContentBeyondSyllabus() {
        return contentBeyondSyllabus;
    }

    /**
     * Set content_beyond_syllabus for Lecture Entity
     * @param contentBeyondSyllabus
     */
    public void setContentBeyondSyllabus(String contentBeyondSyllabus) {
        this.contentBeyondSyllabus = contentBeyondSyllabus;
    }


}
