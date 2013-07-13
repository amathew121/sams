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
import javax.persistence.Id;
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
@Table(name = "attendance_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttendanceView.findAll", query = "SELECT a FROM AttendanceView a"),
    @NamedQuery(name = "AttendanceView.findByIdAttendance", query = "SELECT a FROM AttendanceView a WHERE a.idAttendance = :idAttendance"),
    @NamedQuery(name = "AttendanceView.findByIdLecture", query = "SELECT a FROM AttendanceView a WHERE a.idLecture = :idLecture"),
    @NamedQuery(name = "AttendanceView.findByIdCurrentStudent", query = "SELECT a FROM AttendanceView a WHERE a.idCurrentStudent = :idCurrentStudent"),
    @NamedQuery(name = "AttendanceView.findByIdFacultySubject", query = "SELECT a FROM AttendanceView a WHERE a.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "AttendanceView.findByLectureDate", query = "SELECT a FROM AttendanceView a WHERE a.lectureDate = :lectureDate"),
    @NamedQuery(name = "AttendanceView.findByIdCourse", query = "SELECT a FROM AttendanceView a WHERE a.idCourse = :idCourse"),
    @NamedQuery(name = "AttendanceView.findByAdmnNo", query = "SELECT a FROM AttendanceView a WHERE a.admnNo = :admnNo"),
    @NamedQuery(name = "AttendanceView.findBySemester", query = "SELECT a FROM AttendanceView a WHERE a.semester = :semester"),
    @NamedQuery(name = "AttendanceView.findByDivision", query = "SELECT a FROM AttendanceView a WHERE a.division = :division"),
    @NamedQuery(name = "AttendanceView.findByBatch", query = "SELECT a FROM AttendanceView a WHERE a.batch = :batch"),
    @NamedQuery(name = "AttendanceView.findByRollNo", query = "SELECT a FROM AttendanceView a WHERE a.rollNo = :rollNo")})
public class AttendanceView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_attendance")
    @Id
    private long idAttendance;
    @Column(name = "id_lecture")
    private Integer idLecture;
    @Column(name = "id_current_student")
    private Integer idCurrentStudent;
    @Column(name = "id_faculty_subject")
    private Integer idFacultySubject;
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

    public AttendanceView() {
    }

    public long getIdAttendance() {
        return idAttendance;
    }

    public void setIdAttendance(long idAttendance) {
        this.idAttendance = idAttendance;
    }

    public Integer getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(Integer idLecture) {
        this.idLecture = idLecture;
    }

    public Integer getIdCurrentStudent() {
        return idCurrentStudent;
    }

    public void setIdCurrentStudent(Integer idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    public Integer getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(Integer idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public Date getLectureDate() {
        return lectureDate;
    }

    public void setLectureDate(Date lectureDate) {
        this.lectureDate = lectureDate;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getAdmnNo() {
        return admnNo;
    }

    public void setAdmnNo(String admnNo) {
        this.admnNo = admnNo;
    }

    public short getSemester() {
        return semester;
    }

    public void setSemester(short semester) {
        this.semester = semester;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Short getBatch() {
        return batch;
    }

    public void setBatch(Short batch) {
        this.batch = batch;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
    
}
