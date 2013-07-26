/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piit
 */
@Entity
@Table(name = "coordinator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinator.findAll", query = "SELECT c FROM Coordinator c"),
    @NamedQuery(name = "Coordinator.findByIdFaculty", query = "SELECT c FROM Coordinator c WHERE c.coordinatorPK.idFaculty = :idFaculty"),
    @NamedQuery(name = "Coordinator.findByIdProgram", query = "SELECT c FROM Coordinator c WHERE c.coordinatorPK.idProgram = :idProgram"),
    @NamedQuery(name = "Coordinator.findByIdCourse", query = "SELECT c FROM Coordinator c WHERE c.coordinatorPK.idCourse = :idCourse"),
    @NamedQuery(name = "Coordinator.findBySemester", query = "SELECT c FROM Coordinator c WHERE c.coordinatorPK.semester = :semester"),
    @NamedQuery(name = "Coordinator.findByDivision", query = "SELECT c FROM Coordinator c WHERE c.coordinatorPK.division = :division"),
    @NamedQuery(name = "Coordinator.findByAcYear", query = "SELECT c FROM Coordinator c WHERE c.acYear = :acYear")})
public class Coordinator implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CoordinatorPK coordinatorPK;
    @Column(name = "ac_year")
    private Integer acYear;
    @JoinColumns({
        @JoinColumn(name = "id_program", referencedColumnName = "id_program", insertable = false, updatable = false),
        @JoinColumn(name = "id_course", referencedColumnName = "id_course", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ProgramCourse programCourse;
    @JoinColumn(name = "id_faculty", referencedColumnName = "id_faculty", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Faculty faculty;

    public Coordinator() {
    }

    public Coordinator(CoordinatorPK coordinatorPK) {
        this.coordinatorPK = coordinatorPK;
    }

    public Coordinator(String idFaculty, String idProgram, String idCourse, short semester, String division) {
        this.coordinatorPK = new CoordinatorPK(idFaculty, idProgram, idCourse, semester, division);
    }

    public CoordinatorPK getCoordinatorPK() {
        return coordinatorPK;
    }

    public void setCoordinatorPK(CoordinatorPK coordinatorPK) {
        this.coordinatorPK = coordinatorPK;
    }

    public Integer getAcYear() {
        return acYear;
    }

    public void setAcYear(Integer acYear) {
        this.acYear = acYear;
    }

    public ProgramCourse getProgramCourse() {
        return programCourse;
    }

    public void setProgramCourse(ProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coordinatorPK != null ? coordinatorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinator)) {
            return false;
        }
        Coordinator other = (Coordinator) object;
        if ((this.coordinatorPK == null && other.coordinatorPK != null) || (this.coordinatorPK != null && !this.coordinatorPK.equals(other.coordinatorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Coordinator[ coordinatorPK=" + coordinatorPK + " ]";
    }
    
}
