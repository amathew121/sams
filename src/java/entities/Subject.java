/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ashish
 */
@Entity
@Table(name = "subject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subject.findAll", query = "SELECT s FROM Subject s"),
    @NamedQuery(name = "Subject.findByIdSubject", query = "SELECT s FROM Subject s WHERE s.idSubject = :idSubject"),
    @NamedQuery(name = "Subject.findBySubjectCode", query = "SELECT s FROM Subject s WHERE s.subjectCode = :subjectCode"),
    @NamedQuery(name = "Subject.findBySubjectSrNo", query = "SELECT s FROM Subject s WHERE s.subjectSrNo = :subjectSrNo"),
    @NamedQuery(name = "Subject.findBySubjectName", query = "SELECT s FROM Subject s WHERE s.subjectName = :subjectName"),
    @NamedQuery(name = "Subject.findBySemester", query = "SELECT s FROM Subject s WHERE s.semester = :semester AND s.programCourse=:programCourse ORDER BY s.subjectSrNo" ),
    @NamedQuery(name = "Subject.findByTheory", query = "SELECT s FROM Subject s WHERE s.theory = :theory"),
    @NamedQuery(name = "Subject.findByPractical", query = "SELECT s FROM Subject s WHERE s.practical = :practical"),
    @NamedQuery(name = "Subject.findByTutorial", query = "SELECT s FROM Subject s WHERE s.tutorial = :tutorial"),
    @NamedQuery(name = "Subject.findByElective", query = "SELECT s FROM Subject s WHERE s.elective = :elective"),
    @NamedQuery(name = "Subject.findByCreditTheory", query = "SELECT s FROM Subject s WHERE s.creditTheory = :creditTheory"),
    @NamedQuery(name = "Subject.findByCreditPractical", query = "SELECT s FROM Subject s WHERE s.creditPractical = :creditPractical"),
    @NamedQuery(name = "Subject.findByCreditTutorial", query = "SELECT s FROM Subject s WHERE s.creditTutorial = :creditTutorial")})
public class Subject implements Serializable {

    @Column(name = "test1")
    private Short test1;
    @Column(name = "test2")
    private Short test2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private List<StudentTest> studentTestList;

    @OneToMany(mappedBy = "idSubject")
    private List<SubjectSyllabus> subjectSyllabusList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subject")
    private Integer idSubject;
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
    @Column(name = "semester")
    private short semester;
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
    @Column(name = "credit_theory")
    private short creditTheory;
    @Basic(optional = false)
    @NotNull
    @Column(name = "credit_practical")
    private short creditPractical;
    @Basic(optional = false)
    @NotNull
    @Column(name = "credit_tutorial")
    private short creditTutorial;
    @JoinColumns({
        @JoinColumn(name = "id_program", referencedColumnName = "id_program"),
        @JoinColumn(name = "id_course", referencedColumnName = "id_course")})
    @ManyToOne(optional = false)
    private ProgramCourse programCourse;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSubject")
    private Collection<FacultySubject> facultySubjectCollection;
    @Transient
    private int lectureTotal;

    public int getLectureTotal() {
        return lectureTotal;
    }

    public void setLectureTotal(int lectureTotal) {
        this.lectureTotal = lectureTotal;
    }
    
    
    public Subject() {
    }

    public Subject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    public Subject(Integer idSubject, String subjectCode, String subjectName, short semester, short theory, short practical, short tutorial, boolean elective, short creditTheory, short creditPractical, short creditTutorial) {
        this.idSubject = idSubject;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.semester = semester;
        this.theory = theory;
        this.practical = practical;
        this.tutorial = tutorial;
        this.elective = elective;
        this.creditTheory = creditTheory;
        this.creditPractical = creditPractical;
        this.creditTutorial = creditTutorial;
    }

    public Integer getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
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

    public short getSemester() {
        return semester;
    }

    public void setSemester(short semester) {
        this.semester = semester;
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

    public short getCreditTheory() {
        return creditTheory;
    }

    public void setCreditTheory(short creditTheory) {
        this.creditTheory = creditTheory;
    }

    public short getCreditPractical() {
        return creditPractical;
    }

    public void setCreditPractical(short creditPractical) {
        this.creditPractical = creditPractical;
    }

    public short getCreditTutorial() {
        return creditTutorial;
    }

    public void setCreditTutorial(short creditTutorial) {
        this.creditTutorial = creditTutorial;
    }

    public ProgramCourse getProgramCourse() {
        return programCourse;
    }

    public void setProgramCourse(ProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    @XmlTransient
    public Collection<FacultySubject> getFacultySubjectCollection() {
        return facultySubjectCollection;
    }

    public void setFacultySubjectCollection(Collection<FacultySubject> facultySubjectCollection) {
        this.facultySubjectCollection = facultySubjectCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubject != null ? idSubject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.idSubject == null && other.idSubject != null) || (this.idSubject != null && !this.idSubject.equals(other.idSubject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (programCourse != null) {
            return subjectCode + "/" + programCourse.toString() + "/" + "Sem " + semester ;
        } else {
            return subjectCode + " " + "Sem " + semester + " ";
        }
    }

    @XmlTransient
    public List<SubjectSyllabus> getSubjectSyllabusList() {
        return subjectSyllabusList;
    }

    public void setSubjectSyllabusList(List<SubjectSyllabus> subjectSyllabusList) {
        this.subjectSyllabusList = subjectSyllabusList;
    }


    public Short getTest1() {
        return test1;
    }

    public void setTest1(Short test1) {
        this.test1 = test1;
    }

    public Short getTest2() {
        return test2;
    }

    public void setTest2(Short test2) {
        this.test2 = test2;
    }

    @XmlTransient
    public List<StudentTest> getStudentTestList() {
        return studentTestList;
    }

    public void setStudentTestList(List<StudentTest> studentTestList) {
        this.studentTestList = studentTestList;
    }
    }
