/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty.lecture;

import entities.users.Student;
import controllers.subject.faculty.lecture.CurrentStudentController;
import controllers.subject.faculty.lecture.LectureController;
import entities.subject.ProgramCourse;
import entities.subject.faculty.StudentTest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creates POJO entity for table 'current_student'
 * @author Ashish
 */
@Entity
@Table(name = "current_student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CurrentStudent.findAll", query = "SELECT c FROM CurrentStudent c"),
    @NamedQuery(name = "CurrentStudent.findByIdCurrentStudent", query = "SELECT c FROM CurrentStudent c WHERE c.idCurrentStudent = :idCurrentStudent"),
    @NamedQuery(name = "CurrentStudent.findBySemester", query = "SELECT c FROM CurrentStudent c WHERE c.semester = :semester"),
    @NamedQuery(name = "CurrentStudent.findUltimate", query = "SELECT c FROM CurrentStudent c WHERE c.semester = :semester AND c.division = :division AND c.batch = :batch AND c.programCourse = :programCourse AND c.academicYear = :academicYear ORDER BY c.rollNo"),
    @NamedQuery(name = "CurrentStudent.findUltimateTheory", query = "SELECT c FROM CurrentStudent c WHERE c.semester = :semester AND c.division = :division AND c.programCourse = :programCourse AND c.academicYear = :academicYear ORDER BY c.rollNo"),
    @NamedQuery(name = "CurrentStudent.findByDivision", query = "SELECT c FROM CurrentStudent c WHERE c.division = :division"),
    @NamedQuery(name = "CurrentStudent.findByBatch", query = "SELECT c FROM CurrentStudent c WHERE c.batch = :batch"),
    @NamedQuery(name = "CurrentStudent.findByRollNo", query = "SELECT c FROM CurrentStudent c WHERE c.rollNo = :rollNo"),
    @NamedQuery(name = "CurrentStudent.findByProvisional", query = "SELECT c FROM CurrentStudent c WHERE c.provisional = :provisional"),
    @NamedQuery(name = "CurrentStudent.findByAcademicYear", query = "SELECT c FROM CurrentStudent c WHERE c.academicYear = :academicYear")})
public class CurrentStudent implements Serializable {
    @JoinColumns({
        @JoinColumn(name = "id_proram", referencedColumnName = "id_program"),
        @JoinColumn(name = "id_course", referencedColumnName = "id_course")})
    @ManyToOne(optional = false)
    private ProgramCourse programCourse;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currentStudent")
    private List<StudentTest> studentTestList;
    @Column(name = "roll_no")
    private Integer rollNo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_current_student")
    private Integer idCurrentStudent;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "provisional")
    private boolean provisional;
    @Basic(optional = false)
    @NotNull
    @Column(name = "academic_year")
    @Temporal(TemporalType.DATE)
    private Date academicYear;
    @JoinColumn(name = "admn_no", referencedColumnName = "admn_no")
    @OneToOne(optional = false)
    private Student admnNo;
    @OneToOne(mappedBy = "idCurrentStudent")
    private Attendance attendance;
    @Transient
    private boolean selectedBool;
    @Transient
    private int count;
    @Transient
    private int[] theoryCount = new int[20];
    @Transient
    private int[] pracsCount = new int[20];
    @Transient
    private BigDecimal[] marksAll = new BigDecimal[20];
    @Transient
    private BigDecimal[] marksAll2 = new BigDecimal[20];
    @Transient
    private int theoryCountTotal;
    @Transient
    private BigDecimal marks;
    @Transient
    private short lectureAttended;
    @Transient
    private boolean selectAll;
    @Transient
    private BigDecimal marks2;
    
    @Transient
    private BigDecimal Q1a;
    @Transient
    private BigDecimal Q1b;
    @Transient
    private BigDecimal Q1c;
    @Transient
    private BigDecimal Q1d;
    @Transient
    private BigDecimal Q1e;
    @Transient
    private BigDecimal Q1f;
    @Transient
    private BigDecimal Q2a;
    @Transient
    private BigDecimal Q2b;
    @Transient
    private BigDecimal Q3a;
    @Transient
    private BigDecimal Q3b;
   
    /**
     * TODO:
     * @return
     */
    public int[] getPracsCount() {
        return pracsCount;
    }

    /**
     * TODO:
     * @param pracsCount
     */
    public void setPracsCount(int[] pracsCount) {
        this.pracsCount = pracsCount;
    }
    
    /**
     * Get lecture_attended from CurrentStudent Entity
     * @return
     */
    public short getLectureAttended() {
        return lectureAttended;
    }

    /**
     * Set lecture_attended for CurrentStudent Entity
     * @param lectureAttended
     */
    public void setLectureAttended(short lectureAttended) {
        this.lectureAttended = lectureAttended;
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
     * @return
     */
    public BigDecimal[] getMarksAll2() {
        return marksAll2;
    }

    /**
     * TODO:
     * @param marksAll2
     */
    public void setMarksAll2(BigDecimal[] marksAll2) {
        this.marksAll2 = marksAll2;
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
     * @return
     */
    public BigDecimal[] getMarksAll() {
        return marksAll;
    }

    /**
     * TODO:
     * @param marksAll
     */
    public void setMarksAll(BigDecimal[] marksAll) {
        this.marksAll = marksAll;
    }

    /**
     * TODO:
     * @return
     */
    public BigDecimal getMarks() {
        return marks;
    }

    /**
     * TODO:
     * @param marks
     */
    public void setMarks(BigDecimal marks) {
        this.marks = marks;
    }

    /**
     * TODO:
     * @return
     */
    public BigDecimal getMarks2() {
        return marks2;
    }

    /**
     * TODO:
     * @param marks2
     */
    public void setMarks2(BigDecimal marks2) {
        this.marks2 = marks2;
    }

    /**
     * TODO:
     * @return
     */
    public int getTheoryCountTotal() {
        theoryCountTotal = 0;
        for (int t : theoryCount) {
            theoryCountTotal += t;
        }
        return theoryCountTotal;
    }

    /**
     * TODO:
     * @return
     */
    public int[] getTheoryCount() {
        return theoryCount;
    }

    /**
     * TODO:
     * @param theoryCount
     */
    public void setTheoryCount(int[] theoryCount) {
        this.theoryCount = theoryCount;
    }

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
     * TODO:
     * @return
     */
    public boolean isSelectedBool() {
        return selectedBool;
    }

    /**
     * TODO:
     * @param selectedB
     */
    public void setSelectedB(boolean selectedB) {
        this.selectedBool = selectedB;
    }

    /**
     * Creates CurrentStudent Entity
     */
    public CurrentStudent() {
    }

    /**
     * Creates CurrentStudent entity with the specified 'id_current_student'
     * @param idCurrentStudent
     */
    public CurrentStudent(Integer idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    /**
     * Creates CurrentStudent entity with the specified id_current_student, semester, division, provisional and academic_year
     * @param idCurrentStudent
     * @param semester
     * @param division
     * @param provisional
     * @param academicYear
     */
    public CurrentStudent(Integer idCurrentStudent, short semester, String division, boolean provisional, Date academicYear) {
        this.idCurrentStudent = idCurrentStudent;
        this.semester = semester;
        this.division = division;
        this.provisional = provisional;
        this.academicYear = academicYear;
    }

    /**
     * Get id_current_student from CurrentStudent Entity
     * @return
     */
    public Integer getIdCurrentStudent() {
        return idCurrentStudent;
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

            UIData data = (UIData) event.getComponent().findComponent("listComponents");
            CurrentStudent cs = (CurrentStudent) data.getRowData();
            FacesContext context = FacesContext.getCurrentInstance();
            LectureController lc = (LectureController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "lectureController");

            if (selectAll) {
                for (Lecture lec : lc.getLectureList()) {
                    lec.getChecked().put(cs.getIdCurrentStudent(), Boolean.TRUE);
                    System.out.println(lec.getChecked());

                }

                setSelectAll(true);
                System.out.println("True ");
            } else // If the button is unchecked, unselect all the checkboxes
            {
                for (Lecture lec : lc.getLectureList()) {
                    lec.getChecked().put(((CurrentStudent) data.getRowData()).getIdCurrentStudent(), Boolean.FALSE);
                }
                System.out.println("False ");
                setSelectAll(false);

            }
        }
    }

    /**
     * Set id_current_student for CurrentStudent Entity
     * @param idCurrentStudent
     */
    public void setIdCurrentStudent(Integer idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    /**
     * Get semester from CurrentStudent Entity
     * @return
     */
    public short getSemester() {
        return semester;
    }

    /** 
     * Set semester for CurrentStudent Entity
     * @param semester
     */
    public void setSemester(short semester) {
        this.semester = semester;
    }

    /**
     * Get division from CurrentStudent Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for CurrentStudent Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get batch from CurrentStudent Entity
     * @return
     */
    public Short getBatch() {
        return batch;
    }

    /**
     * Set batch for CurrentStudent Entity
     * @param batch
     */
    public void setBatch(Short batch) {
        this.batch = batch;
    }

    /**
     * Get provisional from CurrentStudent Entity
     * @return
     */
    public boolean getProvisional() {
        return provisional;
    }

    /**
     * Set provisional for CurrentStudent Entity
     * @param provisional
     */
    public void setProvisional(boolean provisional) {
        this.provisional = provisional;
    }

    /**
     * Get academic_year from CurrentStudent Entity
     * @return
     */
    public Date getAcademicYear() {
        return academicYear;
    }

    /**
     * Set academic_year for CurrentStudent Entity
     * @param academicYear
     */
    public void setAcademicYear(Date academicYear) {

        this.academicYear = academicYear;
    }

    /** 
     * Get admn_no from CurrentStudent Entity
     * @return
     */
    public Student getAdmnNo() {
        return admnNo;
    }

    /**
     * Set admn_no for CurrentStudent Entity
     * @param admnNo
     */
    public void setAdmnNo(Student admnNo) {
        this.admnNo = admnNo;
    }

    /**
     * Get attendance from CurrentStudent Entity
     * @return
     */
    public Attendance getAttendance() {
        return attendance;
    }

    /**
     * Set attendance for CurrentStudent Entity
     * @param attendance
     */
    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurrentStudent != null ? idCurrentStudent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrentStudent)) {
            return false;
        }
        CurrentStudent other = (CurrentStudent) object;
        if ((this.idCurrentStudent == null && other.idCurrentStudent != null) || (this.idCurrentStudent != null && !this.idCurrentStudent.equals(other.idCurrentStudent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CurrentStudent[ idCurrentStudent=" + idCurrentStudent + " ]";
    }

    /**
     * Get roll_no from CurrentStudent Entity
     * @return
     */
    public Integer getRollNo() {
        return rollNo;
    }

    /**
     * Set roll_no for CurrentStudent Entity
     * @param rollNo
     */
    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    /**
     * Gets list of StudentTest Entities for a CurrentStudent Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<StudentTest> getStudentTestList() {
        return studentTestList;
    }

    /**
     * Sets list of StudentTest Entities for a CurrentStudent Entity as a foreign key
     * @param studentTestList
     */
    public void setStudentTestList(List<StudentTest> studentTestList) {
        this.studentTestList = studentTestList;
    }

    /**
     * Get a ProgramCourse Entity for a CurrentStudent Entity as a foreign key
     * @return
     */
    public ProgramCourse getProgramCourse() {
        return programCourse;
    }

    /**
     * Set a ProgramCourse Entity for a CurrentStudent Entity as a foreign key
     * @param programCourse
     */
    public void setProgramCourse(ProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    public BigDecimal getQ1a() {
        return Q1a;
    }

    public void setQ1a(BigDecimal Q1a) {
        this.Q1a = Q1a;
    }

    public BigDecimal getQ1b() {
        return Q1b;
    }

    public void setQ1b(BigDecimal Q1b) {
        this.Q1b = Q1b;
    }

    public BigDecimal getQ1c() {
        return Q1c;
    }

    public void setQ1c(BigDecimal Q1c) {
        this.Q1c = Q1c;
    }

    public BigDecimal getQ1d() {
        return Q1d;
    }

    public void setQ1d(BigDecimal Q1d) {
        this.Q1d = Q1d;
    }

    public BigDecimal getQ1e() {
        return Q1e;
    }

    public void setQ1e(BigDecimal Q1e) {
        this.Q1e = Q1e;
    }

    public BigDecimal getQ1f() {
        return Q1f;
    }

    public void setQ1f(BigDecimal Q1f) {
        this.Q1f = Q1f;
    }

    public BigDecimal getQ2a() {
        return Q2a;
    }

    public void setQ2a(BigDecimal Q2a) {
        this.Q2a = Q2a;
    }

    public BigDecimal getQ2b() {
        return Q2b;
    }

    public void setQ2b(BigDecimal Q2b) {
        this.Q2b = Q2b;
    }

    public BigDecimal getQ3a() {
        return Q3a;
    }

    public void setQ3a(BigDecimal Q3a) {
        this.Q3a = Q3a;
    }

    public BigDecimal getQ3b() {
        return Q3b;
    }

    public void setQ3b(BigDecimal Q3b) {
        this.Q3b = Q3b;
    }
}
