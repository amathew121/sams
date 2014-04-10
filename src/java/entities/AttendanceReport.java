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
 * POJO Entity for table 'attendance_report'
 * @author piit
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

    /**
     * TODO:
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * TODO:
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * Creates AttendanceReport Entity
     */
    public AttendanceReport() {
    }

    /** 
     * Get id_attendance from AttendanceReport Entity
     * @return
     */
    public long getIdAttendance() {
        return idAttendance;
    }

    /**
     * Set id_attendance for AttendanceReport Entity
     * @param idAttendance
     */
    public void setIdAttendance(long idAttendance) {
        this.idAttendance = idAttendance;
    }

    /**
     * Get id_current_student from AttendanceReport Entity
     * @return
     */
    public int getIdCurrentStudent() {
        return idCurrentStudent;
    }

    /**
     * Set id_current_student for AttendanceReport Entity
     * @param idCurrentStudent
     */
    public void setIdCurrentStudent(int idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    /**
     * Get id_lecture from AttendanceReport Entity
     * @return
     */
    public int getIdLecture() {
        return idLecture;
    }

    /**
     * Set id_lecture for AttendanceReport Entity
     * @param idLecture
     */
    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    /**
     * Get student_name from AttendanceReport Entity
     * @return
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Set student_name for AttendanceReport Entity
     * @param studentName
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Get id_program from AttendanceReport Entity
     * @return
     */
    public String getIdProgram() {
        return idProgram;
    }

    /**
     * Set id_program for AttendanceReport Entity
     * @param idProgram
     */
    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    /**
     * Get id_course from AttendanceReport Entity
     * @return
     */
    public String getIdCourse() {
        return idCourse;
    }

    /**
     * Set id_course for AttendanceReport Entity
     * @param idCourse
     */
    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    /**
     * Get semester from AttendanceReport Entity
     * @return
     */
    public short getSemester() {
        return semester;
    }

    /**
     * Set semester for AttendanceReport Entity
     * @param semester
     */
    public void setSemester(short semester) {
        this.semester = semester;
    }

    /**
     * Get division from AttendanceReport Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for AttendanceReport Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get batch from AttendanceReport Entity
     * @return
     */
    public Short getBatch() {
        return batch;
    }

    /**
     * Set batch for AttendanceReport Entity
     * @param batch
     */
    public void setBatch(Short batch) {
        this.batch = batch;
    }

    /**
     * Get lecture_date from AttendanceReport Entity
     * @return
     */
    public Date getLectureDate() {
        return lectureDate;
    }

    /**
     * Set lecture_date for AttendanceReport Entity
     * @param lectureDate
     */
    public void setLectureDate(Date lectureDate) {
        this.lectureDate = lectureDate;
    }

    /**
     * Get lecture_start_time from AttendanceReport Entity
     * @return
     */
    public Date getLectureStartTime() {
        return lectureStartTime;
    }

    /**
     * Set lecture_start_time for AttendanceReport Entity
     * @param lectureStartTime
     */
    public void setLectureStartTime(Date lectureStartTime) {
        this.lectureStartTime = lectureStartTime;
    }

    /**
     * Get content_delivered from AttendanceReport Entity
     * @return
     */
    public String getContentDelivered() {
        return contentDelivered;
    }

    /**
     * Set content_delivered for AttendanceReport Entity
     * @param contentDelivered
     */
    public void setContentDelivered(String contentDelivered) {
        this.contentDelivered = contentDelivered;
    }

    /**
     * Get faculty_fname from AttendanceReport Entity
     * @return
     */
    public String getFacultyFname() {
        return facultyFname;
    }

    /**
     * Set faculty_fname for AttendanceReport Entity
     * @param facultyFname
     */
    public void setFacultyFname(String facultyFname) {
        this.facultyFname = facultyFname;
    }

    /**
     * Get faculty_lname from AttendanceReport Entity
     * @return
     */
    public String getFacultyLname() {
        return facultyLname;
    }

    /**
     * Set faculty_lname for AttendanceReport Entity
     * @param facultyLname
     */
    public void setFacultyLname(String facultyLname) {
        this.facultyLname = facultyLname;
    }

    /**
     * Get subject_code from AttendanceReport Entity
     * @return
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * Set subject_code for AttendanceReport Entity
     * @param subjectCode
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * Get theory from AttendanceReport Entity
     * @return
     */
    public short getTheory() {
        return theory;
    }

    /**
     * Set theory for AttendanceReport Entity
     * @param theory
     */
    public void setTheory(short theory) {
        this.theory = theory;
    }

    /**
     * Get practical from AttendanceReport Entity
     * @return
     */
    public short getPractical() {
        return practical;
    }

    /**
     * Set practical for AttendanceReport Entity
     * @param practical
     */
    public void setPractical(short practical) {
        this.practical = practical;
    }

    /**
     * Get tutorial from AttendanceReport Entity
     * @return
     */
    public short getTutorial() {
        return tutorial;
    }

    /**
     * Set tutorial for AttendanceReport Entity
     * @param tutorial
     */
    public void setTutorial(short tutorial) {
        this.tutorial = tutorial;
    }

    /**
     * Get elective from AttendanceReport Entity
     * @return
     */
    public boolean getElective() {
        return elective;
    }

    /**
     * Set elective for AttendanceReport Entity
     * @param elective
     */
    public void setElective(boolean elective) {
        this.elective = elective;
    }

    /**
     * Get id_subject from AttendanceReport Entity
     * @return
     */
    public int getIdSubject() {
        return idSubject;
    }

    /**
     * Set id_subject for AttendanceReport Entity
     * @param idSubject
     */
    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * Get id_faculty from AttendanceReport Entity
     * @return
     */
    public String getIdFaculty() {
        return idFaculty;
    }

    /**
     * Set id_faculty for AttendanceReport Entity
     * @param idFaculty
     */
    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    /**
     * Get id_faculty_subject from AttendanceReport Entity
     * @return
     */
    public int getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject for AttendanceReport Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(int idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    /**
     * Get fs_batch from AttendanceReport Entity
     * @return
     */
    public short getFsBatch() {
        return fsBatch;
    }

    /**
     * Set fs_batch for AttendanceReport Entity
     * @param fsBatch
     */
    public void setFsBatch(short fsBatch) {
        this.fsBatch = fsBatch;
    }

    /**
     * Get roll_no from AttendanceReport Entity
     * @return
     */
    public Integer getRollNo() {
        return rollNo;
    }

    /**
     * Set roll_no for AttendanceReport Entity
     * @param rollNo
     */
    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }
    
}
