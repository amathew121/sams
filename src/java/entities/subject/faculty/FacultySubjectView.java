/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty;

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
 * Creates POJO Entity for table 'faculty_subject_view'
 * @author Ashish
 */
@Entity
@Table(name = "faculty_subject_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacultySubjectView.findAll", query = "SELECT f FROM FacultySubjectView f"),
    @NamedQuery(name = "FacultySubjectView.findByIdFacultySubject", query = "SELECT f FROM FacultySubjectView f WHERE f.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "FacultySubjectView.findByIdFaculty", query = "SELECT f FROM FacultySubjectView f WHERE f.idFaculty = :idFaculty ORDER BY f.idProgram, f.idCourse, f.semester, f.subjectCode, f.division, f.batch"),
    @NamedQuery(name = "FacultySubjectView.findByIdFacultyCurrent", query = "SELECT f FROM FacultySubjectView f WHERE f.idFaculty = :idFaculty AND f.academicYear = 2014 AND MOD(f.semester,2) != 0 ORDER BY f.idProgram, f.idCourse, f.semester, f.subjectCode, f.division, f.batch"),
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "academic_year")
    private int academicYear;
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

    /**
     * Creates FacultySubjectView Entity
     */
    public FacultySubjectView() {
    }

    /**
     * Get id_faculty_subject from FacultySubjectView Entity
     * @return
     */
    public int getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject for FacultySubjectView Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(int idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    /**
     * Get id_faculty from FacultySubjectView Entity
     * @return
     */
    public String getIdFaculty() {
        return idFaculty;
    }

    /**
     * Set id_faculty for FacultySubjectView Entity
     * @param idFaculty
     */
    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    /**
     * Get faculty_title from FacultySubjectView Entity
     * @return
     */
    public String getFacultyTitle() {
        return facultyTitle;
    }

    /**
     * Set faculty_title for FacultySubjectView Entity
     * @param facultyTitle
     */
    public void setFacultyTitle(String facultyTitle) {
        this.facultyTitle = facultyTitle;
    }

    /**
     * Get faculty_lname from FacultySubjectView Entity
     * @return
     */
    public String getFacultyLname() {
        return facultyLname;
    }

    /**
     * Set faculty_lname for FacultySubjectView Entity
     * @param facultyLname
     */
    public void setFacultyLname(String facultyLname) {
        this.facultyLname = facultyLname;
    }

    /**
     * Get faculty_fname from FacultySubjectView Entity
     * @return
     */
    public String getFacultyFname() {
        return facultyFname;
    }

    /**
     * Set faculty_fname for FacultySubjectView Entity
     * @param facultyFname
     */
    public void setFacultyFname(String facultyFname) {
        this.facultyFname = facultyFname;
    }

    /**
     * Get subject_code from FacultySubjectView Entity
     * @return
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * Set subject_code for FacultySubjectView Entity
     * @param subjectCode
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * Get subject_sr_no from FacultySubjectView Entity
     * @return
     */
    public Short getSubjectSrNo() {
        return subjectSrNo;
    }

    /**
     * Set subject_sr_no for FacultySubjectView Entity
     * @param subjectSrNo
     */
    public void setSubjectSrNo(Short subjectSrNo) {
        this.subjectSrNo = subjectSrNo;
    }

    /**
     * Get subject_name from FacultySubjectView Entity
     * @return
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Set subject_name for FacultySubjectView Entity 
     * @param subjectName
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Get id_program from FacultySubjectView Entity
     * @return
     */
    public String getIdProgram() {
        return idProgram;
    }

    /**
     * Set id_program for FacultySubjectView Entity
     * @param idProgram
     */
    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    /**
     * Get id_course from FacultySubjectView Entity
     * @return
     */
    public String getIdCourse() {
        return idCourse;
    }

    /**
     * Set id_course for FacultySubjectView Entity
     * @param idCourse
     */
    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    /**
     * Get semester from FacultySubjectView Entity
     * @return
     */
    public short getSemester() {
        return semester;
    }

    /**
     * Set semester for FacultySubjectView Entity
     * @param semester
     */
    public void setSemester(short semester) {
        this.semester = semester;
    }

    /**
     * Get division from FacultySubjectView Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for FacultySubjectView Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get batch_no from FacultySubjectView Entity
     * @return
     */
    public short getBatchNo() {
        return batch;
    }

    /**
     * Get batch from FacultySubjectView Entity
     * @return
     */
    public String getBatch() {
        String b ;
        if(batch==0)
            return "Theory";
        else
            
            b="Batch" + batch; 
            return b;
    }

    /**
     * Set batch for FacultySubjectView Entity
     * @param batch
     */
    public void setBatch(short batch) {
        this.batch = batch;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }
    
}
