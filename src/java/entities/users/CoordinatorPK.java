/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.users;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ashish Mathew
 */
@Embeddable
public class CoordinatorPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_faculty")
    private String idFaculty;
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

    /**
     * creates CoordinatorPK Entity
     */
    public CoordinatorPK() {
    }

    /**
     * Creates CoordinatorPK Entity with the specified id_faculty, id_program, id_course, semester and division
     * @param idFaculty
     * @param idProgram
     * @param idCourse
     * @param semester
     * @param division
     */
    public CoordinatorPK(String idFaculty, String idProgram, String idCourse, short semester, String division) {
        this.idFaculty = idFaculty;
        this.idProgram = idProgram;
        this.idCourse = idCourse;
        this.semester = semester;
        this.division = division;
    }

    /**
     * Get id_faculty from CoordinatorPK entity
     * @return
     */
    public String getIdFaculty() {
        return idFaculty;
    }

    /**
     * Set id_faculty for CoordinatorPK entity
     * @param idFaculty
     */
    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    /**
     * Get id_faculty from CoordinatorPK entity
     * @return
     */
    public String getIdProgram() {
        return idProgram;
    }

    /**
     * Set id_program for CoordinatorPK entity
     * @param idProgram
     */
    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    /**
     * Get id_course from CoordinatorPK entity
     * @return
     */
    public String getIdCourse() {
        return idCourse;
    }

    /**
     * Set id_course for CoordinatorPK entity
     * @param idCourse
     */
    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    /**
     * Get semester from CoordinatorPK entity
     * @return
     */
    public short getSemester() {
        return semester;
    }

    /**
     * Set semester for CoordinatorPK entity
     * @param semester
     */
    public void setSemester(short semester) {
        this.semester = semester;
    }

    /**
     * Get division from CoordinatorPK entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for CoordinatorPK entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFaculty != null ? idFaculty.hashCode() : 0);
        hash += (idProgram != null ? idProgram.hashCode() : 0);
        hash += (idCourse != null ? idCourse.hashCode() : 0);
        hash += (int) semester;
        hash += (division != null ? division.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoordinatorPK)) {
            return false;
        }
        CoordinatorPK other = (CoordinatorPK) object;
        if ((this.idFaculty == null && other.idFaculty != null) || (this.idFaculty != null && !this.idFaculty.equals(other.idFaculty))) {
            return false;
        }
        if ((this.idProgram == null && other.idProgram != null) || (this.idProgram != null && !this.idProgram.equals(other.idProgram))) {
            return false;
        }
        if ((this.idCourse == null && other.idCourse != null) || (this.idCourse != null && !this.idCourse.equals(other.idCourse))) {
            return false;
        }
        if (this.semester != other.semester) {
            return false;
        }
        if ((this.division == null && other.division != null) || (this.division != null && !this.division.equals(other.division))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idFaculty + ", " + idProgram + ", " + idCourse + ", " + semester + ", " + division;
    }
    
}
