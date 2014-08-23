/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.users;

import entities.subject.ProgramCourse;
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
 * Creates POJO Entity for table 'coordinator'
 *
 * @author piit
 */
@Entity
@Table(name = "coordinator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinator.findAll", query = "SELECT c FROM Coordinator c"),
    @NamedQuery(name = "Coordinator.findByFaculty", query = "SELECT c FROM Coordinator c WHERE c.faculty = :faculty"),
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

    /**
     * Creates Coordinator Entity
     */
    public Coordinator() {
    }

    /**
     * creates Coordinator Entity with the specified coordinatorPK
     *
     * @param coordinatorPK
     */
    public Coordinator(CoordinatorPK coordinatorPK) {
        this.coordinatorPK = coordinatorPK;
    }

    /**
     * creates Coordinator Entity with the specified id_faculty, id_program,
     * id_course, semester and division
     *
     * @param idFaculty
     * @param idProgram
     * @param idCourse
     * @param semester
     * @param division
     */
    public Coordinator(String idFaculty, String idProgram, String idCourse, short semester, String division) {
        this.coordinatorPK = new CoordinatorPK(idFaculty, idProgram, idCourse, semester, division);
    }

    /**
     * Get coordinatorPK from Coordinator Entity
     *
     * @return
     */
    public CoordinatorPK getCoordinatorPK() {
        return coordinatorPK;
    }

    /**
     * Set coordinatorPK for Coordinator Entity
     *
     * @param coordinatorPK
     */
    public void setCoordinatorPK(CoordinatorPK coordinatorPK) {
        this.coordinatorPK = coordinatorPK;
    }

    /**
     * Get ac_year from Coordinator Entity
     *
     * @return
     */
    public Integer getAcYear() {
        return acYear;
    }

    /**
     * Set ac_year for Coordinator Entity
     *
     * @param acYear
     */
    public void setAcYear(Integer acYear) {
        this.acYear = acYear;
    }

    /**
     * Get program_course from Coordinator Entity
     *
     * @return
     */
    public ProgramCourse getProgramCourse() {
        return programCourse;
    }

    /**
     * Set program_course for Coordinator Entity
     *
     * @param programCourse
     */
    public void setProgramCourse(ProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    /**
     * Get faculty from Coordinator Entity
     *
     * @return
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * Set faculty for Coordinator Entity
     *
     * @param faculty
     */
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
        return coordinatorPK.toString();
    }
}
