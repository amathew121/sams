/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.users;

import entities.subject.Subject;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'reviewer' 
 * @author piit
 */
@Entity
@Table(name = "reviewer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reviewer.findAll", query = "SELECT r FROM Reviewer r"),
    @NamedQuery(name = "Reviewer.findByFaculty", query = "SELECT r FROM Reviewer r WHERE r.idFaculty = :idFaculty"),
    @NamedQuery(name = "Reviewer.findAllGroupByFaculty", query = "SELECT r FROM Reviewer r GROUP BY r.idFaculty"),
    @NamedQuery(name = "Reviewer.findByIdReviewer", query = "SELECT r FROM Reviewer r WHERE r.idReviewer = :idReviewer")})
public class Reviewer implements Serializable {
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    @OneToOne(optional = false)
    private Subject idSubject;
    @JoinColumn(name = "id_faculty", referencedColumnName = "id_faculty")
    @OneToOne(optional = false)
    private Faculty idFaculty;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_reviewer")
    private Integer idReviewer;

    /**
     * Creates Reviewer Entity
     */
    public Reviewer() {
    }

    /**
     * Creates Reviewer Entity with the specified 'id_reviewer'
     * @param idReviewer
     */
    public Reviewer(Integer idReviewer) {
        this.idReviewer = idReviewer;
    }

    /**
     * Get id_reviewer from Reviewer Entity
     * @return
     */
    public Integer getIdReviewer() {
        return idReviewer;
    }

    /**
     * Set id_reviewer for Reviewer Entity
     * @param idReviewer
     */
    public void setIdReviewer(Integer idReviewer) {
        this.idReviewer = idReviewer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReviewer != null ? idReviewer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reviewer)) {
            return false;
        }
        Reviewer other = (Reviewer) object;
        if ((this.idReviewer == null && other.idReviewer != null) || (this.idReviewer != null && !this.idReviewer.equals(other.idReviewer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reviewer[ idReviewer=" + idReviewer + " ]";
    }

    /**
     *
     * @return
     */
    public Subject getIdSubject() {
        return idSubject;
    }

    /**
     * 
     * @param idSubject
     */
    public void setIdSubject(Subject idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * 
     * @return
     */
    public Faculty getIdFaculty() {
        return idFaculty;
    }

    /**
     *
     * @param idFaculty
     */
    public void setIdFaculty(Faculty idFaculty) {
        this.idFaculty = idFaculty;
    }
    
}
