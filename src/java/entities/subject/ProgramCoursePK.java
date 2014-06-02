/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ashish
 */
@Embeddable
public class ProgramCoursePK implements Serializable {
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

    /**
     * Creates ProgramCoursePK Entity
     */
    public ProgramCoursePK() {
    }

    /**
     * Creates ProgramCoursePK Entity with the specified id_program and id_course
     * @param idProgram
     * @param idCourse
     */
    public ProgramCoursePK(String idProgram, String idCourse) {
        this.idProgram = idProgram;
        this.idCourse = idCourse;
    }

    /**
     * Get id_program from ProgramCoursePK Entity
     * @return
     */
    public String getIdProgram() {
        return idProgram;
    }

    /**
     * Set id_program for ProgramCoursePK Entity
     * @param idProgram
     */
    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    /**
     * Get id_course from ProgramCoursePK Entity
     * @return
     */
    public String getIdCourse() {
        return idCourse;
    }

    /**
     * Set id_course for ProgramCoursePK Entity
     * @param idCourse
     */
    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgram != null ? idProgram.hashCode() : 0);
        hash += (idCourse != null ? idCourse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramCoursePK)) {
            return false;
        }
        ProgramCoursePK other = (ProgramCoursePK) object;
        if ((this.idProgram == null && other.idProgram != null) || (this.idProgram != null && !this.idProgram.equals(other.idProgram))) {
            return false;
        }
        if ((this.idCourse == null && other.idCourse != null) || (this.idCourse != null && !this.idCourse.equals(other.idCourse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idProgram+" "+idCourse;
    }
    
}
