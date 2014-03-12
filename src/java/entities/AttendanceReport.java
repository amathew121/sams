/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author phcoe
 */
@Entity
@Table(name = "attendance_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttendanceReport.findAll", query = "SELECT a FROM AttendanceReport a"),
    @NamedQuery(name = "AttendanceReport.findByIdAttendance", query = "SELECT a FROM AttendanceReport a WHERE a.idAttendance = :idAttendance"),
    @NamedQuery(name = "AttendanceReport.findByIdCurrentStudent", query = "SELECT a FROM AttendanceReport a WHERE a.idCurrentStudent = :idCurrentStudent"),
    @NamedQuery(name = "AttendanceReport.findByIdLecture", query = "SELECT a FROM AttendanceReport a WHERE a.idLecture = :idLecture"),
    @NamedQuery(name = "AttendanceReport.findByStudentName", query = "SELECT a FROM AttendanceReport a WHERE a.studentName = :studentName"),
    @NamedQuery(name = "AttendanceReport.findByIdProgram", query = "SELECT a FROM AttendanceReport a WHERE a.idProgram = :idProgram"),
    @NamedQuery(name = "AttendanceReport.findByIdCourse", query = "SELECT a FROM AttendanceReport a WHERE a.idCourse = :idCourse"),
    @NamedQuery(name = "AttendanceReport.findBySemester", query = "SELECT a FROM AttendanceReport a WHERE a.semester = :semester"),
    @NamedQuery(name = "AttendanceReport.findByDivision", query = "SELECT a FROM AttendanceReport a WHERE a.division = :division"),
    @NamedQuery(name = "AttendanceReport.findByBatch", query = "SELECT a FROM AttendanceReport a WHERE a.batch = :batch"),
    @NamedQuery(name = "AttendanceReport.findByRollNo", query = "SELECT a FROM AttendanceReport a WHERE a.rollNo = :rollNo"),
    @NamedQuery(name = "AttendanceReport.findByLectureDate", query = "SELECT a FROM AttendanceReport a WHERE a.lectureDate = :lectureDate"),
    @NamedQuery(name = "AttendanceReport.findByLectureStartTime", query = "SELECT a FROM AttendanceReport a WHERE a.lectureStartTime = :lectureStartTime"),
    @NamedQuery(name = "AttendanceReport.findByFacultyFname", query = "SELECT a FROM AttendanceReport a WHERE a.facultyFname = :facultyFname"),
    @NamedQuery(name = "AttendanceReport.findByFacultyLname", query = "SELECT a FROM AttendanceReport a WHERE a.facultyLname = :facultyLname"),
    @NamedQuery(name = "AttendanceReport.findBySubjectCode", query = "SELECT a FROM AttendanceReport a WHERE a.subjectCode = :subjectCode"),
    @NamedQuery(name = "AttendanceReport.findByTheory", query = "SELECT a FROM AttendanceReport a WHERE a.theory = :theory"),
    @NamedQuery(name = "AttendanceReport.findByPractical", query = "SELECT a FROM AttendanceReport a WHERE a.practical = :practical"),
    @NamedQuery(name = "AttendanceReport.findByTutorial", query = "SELECT a FROM AttendanceReport a WHERE a.tutorial = :tutorial"),
    @NamedQuery(name = "AttendanceReport.findByElective", query = "SELECT a FROM AttendanceReport a WHERE a.elective = :elective"),
    @NamedQuery(name = "AttendanceReport.findByIdSubject", query = "SELECT a FROM AttendanceReport a WHERE a.idSubject = :idSubject"),
    @NamedQuery(name = "AttendanceReport.findByIdFaculty", query = "SELECT a FROM AttendanceReport a WHERE a.idFaculty = :idFaculty"),
    @NamedQuery(name = "AttendanceReport.findByIdFacultySubjectCount", query = "SELECT Count(a) FROM AttendanceReport a WHERE a.idFacultySubject = :idFacultySubject GROUP BY a.idCurrentStudent"),
    @NamedQuery(name = "AttendanceReport.findByIdSubjectSemesterDivisionCount", query = "SELECT a,Count(a) FROM AttendanceReport a WHERE a.idProgram = :idProgram and a.idCourse = :idCourse AND a.semester = :semester AND a.idSubject = :idSubject AND a.division = :division GROUP BY a.idCurrentStudent,a.fsBatch ORDER BY a.rollNo"),
    @NamedQuery(name = "AttendanceReport.findByIdSubjectSemesterDivCount", query = "SELECT Count(a) FROM AttendanceReport a WHERE a.semester = :semester AND a.idSubject = :idSubject AND a.division = :division GROUP BY a.idCurrentStudent"),
    @NamedQuery(name = "AttendanceReport.findByIdFacultySubject", query = "SELECT a,Count(a) FROM AttendanceReport a WHERE a.idFacultySubject = :idFacultySubject GROUP BY a.idCurrentStudent ORDER BY a.rollNo")})

public class AttendanceReport implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "fs_batch")
    private short fsBatch;
    @Column(name = "roll_no")
    private Integer rollNo;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_attendance")
    @Id
    private long idAttendance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_current_student")
    private int idCurrentStudent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lecture")
    private int idLecture;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "student_name")
    private String studentName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_program")
    private String idProgram;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_course")
    private String idCourse;
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
    @Column(name = "lecture_date")
    @Temporal(TemporalType.DATE)
    private Date lectureDate;
    @Column(name = "lecture_start_time")
    @Temporal(TemporalType.TIME)
    private Date lectureStartTime;
    @Lob
    @Size(max = 16777215)
    @Column(name = "content_delivered")
    private String contentDelivered;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "faculty_fname")
    private String facultyFname;
    @Size(max = 45)
    @Column(name = "faculty_lname")
    private String facultyLname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "subject_code")
    private String subjectCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "theory")
    private short theory;
    @Basic(optional = false)
    @NotNull
    @Column(name = "practical")
    private short practical;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tutorial")
    private short tutorial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "elective")
    private boolean elective;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_subject")
    private int idSubject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_faculty")
    private String idFaculty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_faculty_subject")
    private int idFacultySubject;

    @Transient
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
    public AttendanceReport() {
    }

    public long getIdAttendance() {
        return idAttendance;
    }

    public void setIdAttendance(long idAttendance) {
        this.idAttendance = idAttendance;
    }

    public int getIdCurrentStudent() {
        return idCurrentStudent;
    }

    public void setIdCurrentStudent(int idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
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

    public String getContentDelivered() {
        return contentDelivered;
    }

    public void setContentDelivered(String contentDelivered) {
        this.contentDelivered = contentDelivered;
    }

    public String getFacultyFname() {
        return facultyFname;
    }

    public void setFacultyFname(String facultyFname) {
        this.facultyFname = facultyFname;
    }

    public String getFacultyLname() {
        return facultyLname;
    }

    public void setFacultyLname(String facultyLname) {
        this.facultyLname = facultyLname;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public short getTheory() {
        return theory;
    }

    public void setTheory(short theory) {
        this.theory = theory;
    }

    public short getPractical() {
        return practical;
    }

    public void setPractical(short practical) {
        this.practical = practical;
    }

    public short getTutorial() {
        return tutorial;
    }

    public void setTutorial(short tutorial) {
        this.tutorial = tutorial;
    }

    public boolean getElective() {
        return elective;
    }

    public void setElective(boolean elective) {
        this.elective = elective;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public String getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public int getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(int idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public short getFsBatch() {
        return fsBatch;
    }

    public void setFsBatch(short fsBatch) {
        this.fsBatch = fsBatch;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }
    
}
