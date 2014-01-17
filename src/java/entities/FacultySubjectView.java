/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ashish
 */
@Entity
@Table(name = "faculty_subject_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacultySubjectView.findAll", query = "SELECT f FROM FacultySubjectView f"),
    @NamedQuery(name = "FacultySubjectView.findByIdFacultySubject", query = "SELECT f FROM FacultySubjectView f WHERE f.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "FacultySubjectView.findByIdFaculty", query = "SELECT f FROM FacultySubjectView f WHERE f.idFaculty = :idFaculty"),
    @NamedQuery(name = "FacultySubjectView.findByIdFacultyEven", query = "SELECT f FROM FacultySubjectView f WHERE f.idFaculty = :idFaculty AND MOD(f.semester,2) = 0"),
    @NamedQuery(name = "FacultySubjectView.findByIdFacultyGroup", query = "SELECT f FROM FacultySubjectView f GROUP BY f.idFaculty"),
    @NamedQuery(name = "FacultySubjectView.findByFacultyTitle", query = "SELECT f FROM FacultySubjectView f WHERE f.facultyTitle = :facultyTitle"),
    @NamedQuery(name = "FacultySubjectView.findByFacultyLname", query = "SELECT f FROM FacultySubjectView f WHERE f.facultyLname = :facultyLname"),
    @NamedQuery(name = "FacultySubjectView.findByFacultyFname", query = "SELECT f FROM FacultySubjectView f WHERE f.facultyFname = :facultyFname"),
    @NamedQuery(name = "FacultySubjectView.findBySubjectCode", query = "SELECT f FROM FacultySubjectView f WHERE f.subjectCode = :subjectCode"),
    @NamedQuery(name = "FacultySubjectView.findBySubjectSrNo", query = "SELECT f FROM FacultySubjectView f WHERE f.subjectSrNo = :subjectSrNo"),
    @NamedQuery(name = "FacultySubjectView.findBySubjectName", query = "SELECT f FROM FacultySubjectView f WHERE f.subjectName = :subjectName"),
    @NamedQuery(name = "FacultySubjectView.findByIdProgram", query = "SELECT f FROM FacultySubjectView f WHERE f.idProgram = :idProgram"),
    @NamedQuery(name = "FacultySubjectView.findByIdCourse", query = "SELECT f FROM FacultySubjectView f WHERE f.idCourse = :idCourse ORDER BY f.semester, f.division, f.subjectName, f.batch"),
    @NamedQuery(name = "FacultySubjectView.findByIdCourseSubject", query = "SELECT f FROM FacultySubjectView f WHERE f.idCourse = :idCourse AND f.subjectCode = :subjectCode"),
    @NamedQuery(name = "FacultySubjectView.findBySemester", query = "SELECT f FROM FacultySubjectView f WHERE f.semester = :semester"),
    @NamedQuery(name = "FacultySubjectView.findByDivision", query = "SELECT f FROM FacultySubjectView f WHERE f.division = :division"),
    @NamedQuery(name = "FacultySubjectView.findByBatch", query = "SELECT f FROM FacultySubjectView f WHERE f.batch = :batch")})
public class FacultySubjectView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_faculty_subject")
    @Id
    private int idFacultySubject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_faculty")
    private String idFaculty;
    @Size(max = 45)
    @Column(name = "faculty_title")
    private String facultyTitle;
    @Size(max = 45)
    @Column(name = "faculty_lname")
    private String facultyLname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "faculty_fname")
    private String facultyFname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "subject_code")
    private String subjectCode;
    @Column(name = "subject_sr_no")
    private Short subjectSrNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "subject_name")
    private String subjectName;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "batch")
    private short batch;

    public FacultySubjectView() {
    }

    public int getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(int idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public String getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public String getFacultyTitle() {
        return facultyTitle;
    }

    public void setFacultyTitle(String facultyTitle) {
        this.facultyTitle = facultyTitle;
    }

    public String getFacultyLname() {
        return facultyLname;
    }

    public void setFacultyLname(String facultyLname) {
        this.facultyLname = facultyLname;
    }

    public String getFacultyFname() {
        return facultyFname;
    }

    public void setFacultyFname(String facultyFname) {
        this.facultyFname = facultyFname;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Short getSubjectSrNo() {
        return subjectSrNo;
    }

    public void setSubjectSrNo(Short subjectSrNo) {
        this.subjectSrNo = subjectSrNo;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public short getBatchNo() {
        return batch;
    }
    public String getBatch() {
        String b ;
        if(batch==0)
            return "Theory";
        else
            
            b="Batch" + batch; 
            return b;
    }

    public void setBatch(short batch) {
        this.batch = batch;
    }
    
}
