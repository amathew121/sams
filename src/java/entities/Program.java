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
 *
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

    public Program() {
    }

    public Program(String idProgram) {
        this.idProgram = idProgram;
    }

    public Program(String idProgram, String programName) {
        this.idProgram = idProgram;
        this.programName = programName;
    }

    public String getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Short getNoOfSemesters() {
        return noOfSemesters;
    }

    public void setNoOfSemesters(Short noOfSemesters) {
        this.noOfSemesters = noOfSemesters;
    }

    @XmlTransient
    public Collection<ProgramCourse> getProgramCourseCollection() {
        return programCourseCollection;
    }

    public void setProgramCourseCollection(Collection<ProgramCourse> programCourseCollection) {
        this.programCourseCollection = programCourseCollection;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

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

    @XmlTransient
    public List<Feedback2013Question> getFeedback2013QuestionList() {
        return feedback2013QuestionList;
    }

    public void setFeedback2013QuestionList(List<Feedback2013Question> feedback2013QuestionList) {
        this.feedback2013QuestionList = feedback2013QuestionList;
    }
    
}
