/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO entity for table 'attendance_view'
 * @author Ashish
 */
@Entity
@Table(name = "attendance_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttendanceView.findAll", query = "SELECT a FROM AttendanceView a"),
    @NamedQuery(name = "AttendanceView.findByIdAttendance", query = "SELECT a FROM AttendanceView a WHERE a.idAttendance = :idAttendance"),
    @NamedQuery(name = "AttendanceView.findByIdLecture", query = "SELECT a FROM AttendanceView a WHERE a.idLecture = :idLecture"),
    @NamedQuery(name = "AttendanceView.findByIdCurrentStudent", query = "SELECT a FROM AttendanceView a WHERE a.idCurrentStudent = :idCurrentStudent"),
    @NamedQuery(name = "AttendanceView.findByIdFacultySubjectCount", query = "SELECT count(a.idCurrentStudent) FROM AttendanceView a WHERE a.idFacultySubject = :idFacultySubject AND a.idLecture= :idLecture "),
    @NamedQuery(name = "AttendanceView.findByIdFacultySubject", query = "SELECT a FROM AttendanceView a WHERE a.idFacultySubject = :idFacultySubject GROUP BY a.idLecture ORDER BY a.lectureDate,a.lectureStartTime"),
    @NamedQuery(name = "AttendanceView.findByIdFacultySubjectLecture", query = "SELECT a FROM AttendanceView a WHERE a.idFacultySubject = :idFacultySubject AND a.idLecture= :idLecture"),
    @NamedQuery(name = "AttendanceView.findByLectureDate", query = "SELECT a FROM AttendanceView a WHERE a.lectureDate = :lectureDate"),
    @NamedQuery(name = "AttendanceView.findByIdCourse", query = "SELECT a FROM AttendanceView a WHERE a.idCourse = :idCourse"),
    @NamedQuery(name = "AttendanceView.findByAdmnNo", query = "SELECT a FROM AttendanceView a WHERE a.admnNo = :admnNo"),
    @NamedQuery(name = "AttendanceView.findBySemester", query = "SELECT a FROM AttendanceView a WHERE a.semester = :semester"),
    @NamedQuery(name = "AttendanceView.findByDivision", query = "SELECT a FROM AttendanceView a WHERE a.division = :division"),
    @NamedQuery(name = "AttendanceView.findByBatch", query = "SELECT a FROM AttendanceView a WHERE a.batch = :batch"),
    @NamedQuery(name = "AttendanceView.findByRollNo", query = "SELECT a FROM AttendanceView a WHERE a.rollNo = :rollNo")})
public class AttendanceView implements Serializable {
    @Column(name = "id_attendance")
    @Id
    private BigInteger idAttendance;
    @Column(name = "lecture_start_time")
    @Temporal(TemporalType.TIME)
    private Date lectureStartTime;
    private static final long serialVersionUID = 1L;
    @Column(name = "id_lecture")
    private Integer idLecture;
    @Column(name = "id_current_student")
    private Integer idCurrentStudent;
    @Column(name = "id_faculty_subject")
    private Integer idFacultySubject;
    @Lob
    @Size(max = 16777215)
    @Column(name = "content_delivered")
    private String contentDelivered;
    @Column(name = "lecture_date")
    @Temporal(TemporalType.DATE)
    private Date lectureDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_course")
    private String idCourse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "admn_no")
    private String admnNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "semester")
    private short semester;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "division")
    private String division;
    @Column(name = "batch")
    private Short batch;
    @Size(max = 10)
    @Column(name = "roll_no")
    private String rollNo;

    /**
     * Creates AttendanceView Entity
     */
    public AttendanceView() {
    }

    /**
     * Get id_lecture from AttendanceView Entity
     * @return
     */
    public Integer getIdLecture() {
        return idLecture;
    }

    /**
     * Set id_lecture for AttendanceView Entity
     * @param idLecture
     */
    public void setIdLecture(Integer idLecture) {
        this.idLecture = idLecture;
    }

    /**
     * Set id_lecture from AttendanceView Entity
     * @return
     */
    public Integer getIdCurrentStudent() {
        return idCurrentStudent;
    }

    /**
     * Set id_current_student for AttendanceView Entity
     * @param idCurrentStudent
     */
    public void setIdCurrentStudent(Integer idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    /**
     * Get id_faculty_student from AttendanceView Entity
     * @return
     */
    public Integer getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject for AttendanceView Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(Integer idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    /**
     * Get lecture_date from AttendanceView Entity
     * @return
     */
    public Date getLectureDate() {
        return lectureDate;
    }

    /**
     * Set lecture_date for AttendanceView Entity
     * @param lectureDate
     */
    public void setLectureDate(Date lectureDate) {
        this.lectureDate = lectureDate;
    }

    /**
     * Get id_course from AttendanceView Entity
     * @return
     */
    public String getIdCourse() {
        return idCourse;
    }

    /**
     * Set id_course for AttendanceView Entity
     * @param idCourse
     */
    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    /**
     * Get admn_no from AttendanceView Entity
     * @return
     */
    public String getAdmnNo() {
        return admnNo;
    }

    /**
     * Set admn_no for AttendanceView Entity
     * @param admnNo
     */
    public void setAdmnNo(String admnNo) {
        this.admnNo = admnNo;
    }

    /**
     * Get semester from AttendanceView Entity
     * @return
     */
    public short getSemester() {
        return semester;
    }

    /**
     * Set semester for AttendanceView Entity
     * @param semester
     */
    public void setSemester(short semester) {
        this.semester = semester;
    }

    /**
     * Get division from AttendanceView Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for AttendanceView Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get batch from AttendanceView Entity
     * @return
     */
    public Short getBatch() {
        return batch;
    }

    /**
     * Set batch for AttendanceView Entity
     * @param batch
     */
    public void setBatch(Short batch) {
        this.batch = batch;
    }

    /**
     * Get roll_no from AttendanceView Entity
     * @return
     */
    public String getRollNo() {
        return rollNo;
    }

    /**
     * Set roll_no for AttendanceView Entity
     * @param rollNo
     */
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    /**
     * Get content_delivered from AttendanceView Entity
     * @return
     */
    public String getContentDelivered() {
        return contentDelivered;
    }

    /**
     * Set content_delivered for AttendanceView Entity
     * @param contentDelivered
     */
    public void setContentDelivered(String contentDelivered) {
        this.contentDelivered = contentDelivered;
    }

    /**
     * Get id_attendance from AttendanceView Entity
     * @return
     */
    public BigInteger getIdAttendance() {
        return idAttendance;
    }

    /**
     * Set id_attendance for AttendanceView Entity
     * @param idAttendance
     */
    public void setIdAttendance(BigInteger idAttendance) {
        this.idAttendance = idAttendance;
    }

    /**
     * Get lecture_start_time from AttendanceView Entity
     * @return
     */
    public Date getLectureStartTime() {
        return lectureStartTime;
    }

    /**
     * Set lecture_start_time for AttendanceView Entity
     * @param lectureStartTime
     */
    public void setLectureStartTime(Date lectureStartTime) {
        this.lectureStartTime = lectureStartTime;
    }

}