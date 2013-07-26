/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
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
 *
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
    private Collection<Coordinator> coordinatorCollection;
    private static final long serialVersionUID = 1L;
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

    public ProgramCourse() {
    }

    public ProgramCourse(ProgramCoursePK programCoursePK) {
        this.programCoursePK = programCoursePK;
    }

    public ProgramCourse(String idProgram, String idCourse) {
        this.programCoursePK = new ProgramCoursePK(idProgram, idCourse);
    }

    public ProgramCoursePK getProgramCoursePK() {
        return programCoursePK;
    }

    public void setProgramCoursePK(ProgramCoursePK programCoursePK) {
        this.programCoursePK = programCoursePK;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @XmlTransient
    public Collection<Subject> getSubjectCollection() {
        return subjectCollection;
    }

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
        return program.getIdProgram() + " " + course.getCourseName();
    }

    @XmlTransient
    public Collection<Coordinator> getCoordinatorCollection() {
        return coordinatorCollection;
    }

    public void setCoordinatorCollection(Collection<Coordinator> coordinatorCollection) {
        this.coordinatorCollection = coordinatorCollection;
    }
    
}
