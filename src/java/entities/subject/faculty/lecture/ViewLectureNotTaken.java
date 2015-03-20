/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty.lecture;

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
 * @author piit
 */
@Entity
@Table(name = "view_lecture_not_taken")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewLectureNotTaken.findAll", query = "SELECT v FROM ViewLectureNotTaken v"),
    @NamedQuery(name = "ViewLectureNotTaken.findByIdProgram", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.idProgram = :idProgram"),
    @NamedQuery(name = "ViewLectureNotTaken.findByIdProgramCourse", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.idProgram = :program AND v.idCourse = :course AND v.semester = :sem"),
    @NamedQuery(name = "ViewLectureNotTaken.findByIdCourse", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.idCourse = :idCourse"),
    @NamedQuery(name = "ViewLectureNotTaken.findBySemester", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.semester = :semester"),
    @NamedQuery(name = "ViewLectureNotTaken.findByDivision", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.division = :division"),
    @NamedQuery(name = "ViewLectureNotTaken.findByBatch", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.batch = :batch"),
    @NamedQuery(name = "ViewLectureNotTaken.findByIdSubject", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.idSubject = :idSubject"),
    @NamedQuery(name = "ViewLectureNotTaken.findByIdFaculty", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.idFaculty = :idFaculty order by v.calDate, v.division"),
    @NamedQuery(name = "ViewLectureNotTaken.findByDayNum", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.dayNum = :dayNum"),
    @NamedQuery(name = "ViewLectureNotTaken.findByTimeStart", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.timeStart = :timeStart"),
    @NamedQuery(name = "ViewLectureNotTaken.findByCalDate", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.calDate = :calDate"),
    @NamedQuery(name = "ViewLectureNotTaken.findByIdTt", query = "SELECT v FROM ViewLectureNotTaken v WHERE v.idTt = :idTt")})
public class ViewLectureNotTaken implements Serializable {

    @Size(max = 91)
    @Column(name = "faculty_name")
    private String facultyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "subject_code")
    private String subjectCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "subject_name")
    private String subjectName;
    private static final long serialVersionUID = 1L;
    @Size(max = 10)
    @Column(name = "id_program")
    private String idProgram;
    @Size(max = 10)
    @Column(name = "id_course")
    private String idCourse;
    @Column(name = "semester")
    private Short semester;
    @Size(max = 1)
    @Column(name = "division")
    private String division;
    @Column(name = "batch")
    private Short batch;
    @Column(name = "id_subject")
    private Integer idSubject;
    @Size(max = 20)
    @Column(name = "id_faculty")
    private String idFaculty;
    @Column(name = "day_num")
    private Short dayNum;
    @Column(name = "time_start")
    @Id
    @Temporal(TemporalType.TIME)
    private Date timeStart;
    @Column(name = "cal_date")
    @Id
    @Temporal(TemporalType.DATE)
    private Date calDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tt")
    @Id
    private int idTt;

    public ViewLectureNotTaken() {
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

    public Short getSemester() {
        return semester;
    }

    public void setSemester(Short semester) {
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

    public String getBatchDetail() {
        if (batch == 0) {
            return "Theory";
        } else {
            return "" + batch;
        }
    }

    public void setBatch(Short batch) {
        this.batch = batch;
    }

    public Integer getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    public String getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public Short getDayNum() {
        return dayNum;
    }

    public void setDayNum(Short dayNum) {
        this.dayNum = dayNum;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public int getIdTt() {
        return idTt;
    }

    public void setIdTt(int idTt) {
        this.idTt = idTt;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
