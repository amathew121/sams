/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject;

import entities.feedback.Feedback2013Question;
import entities.users.Student;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creates POJO Entity for table 'program'
 * @author Ashish
 */
@Entity
@Table(name = "program")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Program.findAll", query = "SELECT p FROM Program p"),
    @NamedQuery(name = "Program.findByIdProgram", query = "SELECT p FROM Program p WHERE p.idProgram = :idProgram"),
    @NamedQuery(name = "Program.findByProgramName", query = "SELECT p FROM Program p WHERE p.programName = :programName"),
    @NamedQuery(name = "Program.findByNoOfSemesters", query = "SELECT p FROM Program p WHERE p.noOfSemesters = :noOfSemesters")})
public class Program implements Serializable {
    @OneToMany(mappedBy = "idProgram")
    private List<Feedback2013Question> feedback2013QuestionList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_program")
    private String idProgram;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "program_name")
    private String programName;
    @Column(name = "no_of_semesters")
    private Short noOfSemesters;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "program")
    private Collection<ProgramCourse> programCourseCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProgram")
    private Collection<Student> studentCollection;

    /**
     * Creates program Entity
     */
    public Program() {
    }

    /**
     * Creates program Entity with the specified 'id_program'
     * @param idProgram
     */
    public Program(String idProgram) {
        this.idProgram = idProgram;
    }

    /**
     * Creates program Entity with the specified id_program and program_name 
     * @param idProgram
     * @param programName
     */
    public Program(String idProgram, String programName) {
        this.idProgram = idProgram;
        this.programName = programName;
    }

    /**
     * Get id_program from Program entity
     * @return
     */
    public String getIdProgram() {
        return idProgram;
    }

    /**
     * Set id_program for Program Entity
     * @param idProgram
     */
    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    /**
     * Get program_name from Program Entity
     * @return
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Set program_name for Program Entity
     * @param programName
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * Get no_of_semesters from Program Entity
     * @return
     */
    public Short getNoOfSemesters() {
        return noOfSemesters;
    }

    /**
     * Set no_of_semesters for Program Entity
     * @param noOfSemesters
     */
    public void setNoOfSemesters(Short noOfSemesters) {
        this.noOfSemesters = noOfSemesters;
    }

    /**
     * Gets collection of ProgramCourse Entities for the Program Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<ProgramCourse> getProgramCourseCollection() {
        return programCourseCollection;
    }

    /**
     * Sets collection of ProgramCourse Entities for the Program Entity as a foreign key
     * @param programCourseCollection
     */
    public void setProgramCourseCollection(Collection<ProgramCourse> programCourseCollection) {
        this.programCourseCollection = programCourseCollection;
    }

    /**
     * Gets collection of Student Entities for the Program Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    /**
     * Sets collection of Student Entities for the Program Entity as a foreign key
     * @param studentCollection
     */
    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgram != null ? idProgram.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Program)) {
            return false;
        }
        Program other = (Program) object;
        if ((this.idProgram == null && other.idProgram != null) || (this.idProgram != null && !this.idProgram.equals(other.idProgram))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idProgram;
    }

    /**
     * Gets list of Feedback2013Question Entities for the Program entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<Feedback2013Question> getFeedback2013QuestionList() {
        return feedback2013QuestionList;
    }

    /**
     * Sets list of Feedback2013Question Entities for the Program entity as a foreign key
     * @param feedback2013QuestionList
     */
    public void setFeedback2013QuestionList(List<Feedback2013Question> feedback2013QuestionList) {
        this.feedback2013QuestionList = feedback2013QuestionList;
    }
    
}
