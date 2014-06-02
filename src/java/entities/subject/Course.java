/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject;

import entities.subject.ProgramCourse;
import java.io.Serializable;
import java.util.Collection;
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
 * Creates POJO entity for table 'course'
 * @author Ashish
 */
@Entity
@Table(name = "course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c"),
    @NamedQuery(name = "Course.findByIdCourse", query = "SELECT c FROM Course c WHERE c.idCourse = :idCourse"),
    @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.courseName = :courseName")})
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_course")
    private String idCourse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "course_name")
    private String courseName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<ProgramCourse> programCourseCollection;

    /**
     * Creates Course Entity
     */
    public Course() {
    }

    /**
     * Creates Course entity with the specified id_course
     * @param idCourse
     */
    public Course(String idCourse) {
        this.idCourse = idCourse;
    }

    /**
     * Creates Course Entity with the specified id_course and course_name 
     * @param idCourse
     * @param courseName
     */
    public Course(String idCourse, String courseName) {
        this.idCourse = idCourse;
        this.courseName = courseName;
    }

    /**
     * Get id_course from Course Entity
     * @return
     */
    public String getIdCourse() {
        return idCourse;
    }

    /**
     * Set id_course for Course Entity
     * @param idCourse
     */
    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    /**
     * Get course_name from Course Entity
     * @return
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Set course_name for Course Entity
     * @param courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets collection of ProgramCourse entities for the course entity as a foreign Key.
     * @return
     */
    @XmlTransient
    public Collection<ProgramCourse> getProgramCourseCollection() {
        return programCourseCollection;
    }

    /**
     * Sets collection of ProgramCourse entities for the course entity as a foreign key.
     * @param programCourseCollection
     */
    public void setProgramCourseCollection(Collection<ProgramCourse> programCourseCollection) {
        this.programCourseCollection = programCourseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCourse != null ? idCourse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.idCourse == null && other.idCourse != null) || (this.idCourse != null && !this.idCourse.equals(other.idCourse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return courseName;
    }
    
}
