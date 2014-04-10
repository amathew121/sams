/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creates POJO Entity for table 'program_course'
 * @author Ashish
 */
@Entity
@Table(name = "program_course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramCourse.findAll", query = "SELECT p FROM ProgramCourse p"),
    @NamedQuery(name = "ProgramCourse.findByIdProgram", query = "SELECT p FROM ProgramCourse p WHERE p.programCoursePK.idProgram = :idProgram"),
    @NamedQuery(name = "ProgramCourse.findByIdCourse", query = "SELECT p FROM ProgramCourse p WHERE p.programCoursePK.idCourse = :idCourse")})
public class ProgramCourse implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programCourse")
    private List<CurrentStudent> currentStudentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programCourse")
    private List<Feedback2013Student> feedback2013StudentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programCourse")
    private Collection<Coordinator> coordinatorCollection;
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @EmbeddedId
    protected ProgramCoursePK programCoursePK;
    @JoinColumn(name = "id_program", referencedColumnName = "id_program", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Program program;
    @JoinColumn(name = "id_course", referencedColumnName = "id_course", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course course;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programCourse")
    private Collection<Subject> subjectCollection;

    /**
     * Creates ProgramCourse Entity
     */
    public ProgramCourse() {
    }

    /**
     * Creates ProgramCourse Entity with the specified programCoursePK
     * @param programCoursePK
     */
    public ProgramCourse(ProgramCoursePK programCoursePK) {
        this.programCoursePK = programCoursePK;
    }

    /**
     * Creates ProgramCourse Entity with the specified id_program and id_course
     * @param idProgram
     * @param idCourse
     */
    public ProgramCourse(String idProgram, String idCourse) {
        this.programCoursePK = new ProgramCoursePK(idProgram, idCourse);
    }

    /**
     * Get ProgramCoursePK from ProgramCourse Entity
     * @return
     */
    public ProgramCoursePK getProgramCoursePK() {
        return programCoursePK;
    }

    /**
     * Set ProgramCoursePK from ProgramCourse Entity
     * @param programCoursePK
     */
    public void setProgramCoursePK(ProgramCoursePK programCoursePK) {
        this.programCoursePK = programCoursePK;
    }

    /**
     * Get program from ProgramCourse Entity
     * @return
     */
    public Program getProgram() {
        return program;
    }

    /**
     * Set program for ProgramCourse Entity
     * @param program
     */
    public void setProgram(Program program) {
        this.program = program;
    }

    /**
     * Get course from ProgramCourse Entity
     * @return
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Set course for ProgramCourse Entity
     * @param course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets collection of Subject Entities for the ProgramCourse Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<Subject> getSubjectCollection() {
        return subjectCollection;
    }

    /**
     * Sets collection of Subject Entities for the ProgramCourse Entity as a foreign key
     * @param subjectCollection
     */
    public void setSubjectCollection(Collection<Subject> subjectCollection) {
        this.subjectCollection = subjectCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programCoursePK != null ? programCoursePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramCourse)) {
            return false;
        }
        ProgramCourse other = (ProgramCourse) object;
        if ((this.programCoursePK == null && other.programCoursePK != null) || (this.programCoursePK != null && !this.programCoursePK.equals(other.programCoursePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return program.getIdProgram() + "/" + course.getIdCourse();
    }

    /**
     * Gets collection of Coordinator Entities for the ProgramCourse Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<Coordinator> getCoordinatorCollection() {
        return coordinatorCollection;
    }

    /**
     * Sets collection of Coordinator Entities for the ProgramCourse Entity as a foreign key
     * @param coordinatorCollection
     */
    public void setCoordinatorCollection(Collection<Coordinator> coordinatorCollection) {
        this.coordinatorCollection = coordinatorCollection;
    }

    /**
     * Gets list of Feedback2013Student Entities for the ProgramCourse Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<Feedback2013Student> getFeedback2013StudentList() {
        return feedback2013StudentList;
    }

    /**
     * Sets list of Feedback2013Student Entities for the ProgramCourse Entity as a foreign key
     * @param feedback2013StudentList
     */
    public void setFeedback2013StudentList(List<Feedback2013Student> feedback2013StudentList) {
        this.feedback2013StudentList = feedback2013StudentList;
    }

    /**
     * Gets list of CurrentStudent Entities for the ProgramCourse Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<CurrentStudent> getCurrentStudentList() {
        return currentStudentList;
    }

    /**
     * Sets list of CurrentStudent Entities for the ProgramCourse Entity as a foreign key
     * @param currentStudentList
     */
    public void setCurrentStudentList(List<CurrentStudent> currentStudentList) {
        this.currentStudentList = currentStudentList;
    }
    
}
