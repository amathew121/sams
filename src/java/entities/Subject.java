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
 * POJO Entity for table 'subject'
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
    @NamedQuery(name = "Subject.findByProgramCourse", query = "SELECT s FROM Subject s WHERE s.programCourse=:programCourse AND s.theory > 0 ORDER BY s.subjectSrNo"),
    @NamedQuery(name = "Subject.findBySemester", query = "SELECT s FROM Subject s WHERE s.semester = :semester AND s.programCourse=:programCourse ORDER BY s.subjectSrNo " ),
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

    /**
     * Get lecture_total from Subject Entity
     * @return
     */
    public int getLectureTotal() {
        return lectureTotal;
    }

    /**
     * Set lecture_total for Subject Entity
     * @param lectureTotal
     */
    public void setLectureTotal(int lectureTotal) {
        this.lectureTotal = lectureTotal;
    }
    
    /**
     * Creates Subject Entity
     */
    public Subject() {
    }

    /**
     * Creates Subject Entity with the specified id_subject
     * @param idSubject
     */
    public Subject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * creates Subject Entity with the specified id_subject, subject_code, subject_name, semester, theory, practical, tutorial, credit_theory, credit_practical, credit_elective and credit_tutorial
     * @param idSubject
     * @param subjectCode
     * @param subjectName
     * @param semester
     * @param theory
     * @param practical
     * @param tutorial
     * @param elective
     * @param creditTheory
     * @param creditPractical
     * @param creditTutorial
     */
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

    /**
     * Get id_subject from Subject Entity
     * @return
     */
    public Integer getIdSubject() {
        return idSubject;
    }

    /**
     * Set id_subject for Subject Entity
     * @param idSubject
     */
    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * Get subject_code from Subject Entity
     * @return
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * Set subject_code for Subject Entity
     * @param subjectCode
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * Get subject_sr_no from Subject Entity
     * @return
     */
    public Short getSubjectSrNo() {
        return subjectSrNo;
    }

    /**
     * Set subject_sr_no for Subject Entity
     * @param subjectSrNo
     */
    public void setSubjectSrNo(Short subjectSrNo) {
        this.subjectSrNo = subjectSrNo;
    }

    /**
     * Get subject_name from Subject Entity
     * @return
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Set subject_name for Subject Entity
     * @param subjectName
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Get semester from Subject Entity
     * @return
     */
    public short getSemester() {
        return semester;
    }

    /**
     * Set semester for Subject Entity
     * @param semester
     */
    public void setSemester(short semester) {
        this.semester = semester;
    }

    /**
     * Get theory from Subject Entity
     * @return
     */
    public short getTheory() {
        return theory;
    }

    /**
     * Set theory for Subject Entity
     * @param theory
     */
    public void setTheory(short theory) {
        this.theory = theory;
    }

    /**
     * Get practical from Subject Entity
     * @return
     */
    public short getPractical() {
        return practical;
    }

    /**
     * Set practical for Subject Entity
     * @param practical
     */
    public void setPractical(short practical) {
        this.practical = practical;
    }

    /**
     * Get tutorial from Subject Entity
     * @return
     */
    public short getTutorial() {
        return tutorial;
    }

    /**
     * Set tutorial for Subject Entity
     * @param tutorial
     */
    public void setTutorial(short tutorial) {
        this.tutorial = tutorial;
    }

    /**
     * Get elective from Subject Entity
     * @return
     */
    public boolean getElective() {
        return elective;
    }

    /**
     * Set elective for Subject Entity
     * @param elective
     */
    public void setElective(boolean elective) {
        this.elective = elective;
    }

    /**
     * Get credit_theory from Subject Entity
     * @return
     */
    public short getCreditTheory() {
        return creditTheory;
    }

    /**
     * Set credit_theory for Subject Entity
     * @param creditTheory
     */
    public void setCreditTheory(short creditTheory) {
        this.creditTheory = creditTheory;
    }

    /**
     * Get credit_practical from Subject Entity
     * @return
     */
    public short getCreditPractical() {
        return creditPractical;
    }

    /**
     * Set credit_practical for Subject Entity
     * @param creditPractical
     */
    public void setCreditPractical(short creditPractical) {
        this.creditPractical = creditPractical;
    }

    /**
     * Get credit_tutorial from Subject Entity
     * @return
     */
    public short getCreditTutorial() {
        return creditTutorial;
    }

    /**
     * Set credit_tutorial for Subject Entity
     * @param creditTutorial
     */
    public void setCreditTutorial(short creditTutorial) {
        this.creditTutorial = creditTutorial;
    }

    /**
     * Get ProgramCourse from Subject Entity
     * @return
     */
    public ProgramCourse getProgramCourse() {
        return programCourse;
    }

    /**
     * Set ProgramCourse for Subject Entity
     * @param programCourse
     */
    public void setProgramCourse(ProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    /**
     * Gets collection of FacultySubject Entities for the Subject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<FacultySubject> getFacultySubjectCollection() {
        return facultySubjectCollection;
    }

    /**
     * Sets collection of FacultySubject Entities for the Subject Entity as a foreign key
     * @param facultySubjectCollection
     */
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

    /**
     * Gets collection of SubjectSyllabus Entities for the Subject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<SubjectSyllabus> getSubjectSyllabusList() {
        return subjectSyllabusList;
    }

    /**
     * Sets collection of SubjectSyllabus Entities for the Subject Entity as a foreign key
     * @param subjectSyllabusList
     */
    public void setSubjectSyllabusList(List<SubjectSyllabus> subjectSyllabusList) {
        this.subjectSyllabusList = subjectSyllabusList;
    }

    /**
     * TODO:
     * @return
     */
    public Short getTest1() {
        return test1;
    }

    /**
     * TODO:
     * @param test1
     */
    public void setTest1(Short test1) {
        this.test1 = test1;
    }

    /**
     * TODO:
     * @return
     */
    public Short getTest2() {
        return test2;
    }

    /**
     * TODO:
     * @param test2
     */
    public void setTest2(Short test2) {
        this.test2 = test2;
    }

    /**
     * Gets list of StudentTest Entities for the Subject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<StudentTest> getStudentTestList() {
        return studentTestList;
    }

    /**
     * Sets list of StudentTest Entities for the Subject Entity as a foreign key
     * @param studentTestList
     */
    public void setStudentTestList(List<StudentTest> studentTestList) {
        this.studentTestList = studentTestList;
    }
    }
