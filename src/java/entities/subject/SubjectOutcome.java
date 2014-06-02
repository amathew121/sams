/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'subject_outcome'
 * @author piit
 */
@Entity
@Table(name = "subject_outcome")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubjectOutcome.findAll", query = "SELECT s FROM SubjectOutcome s"),
    @NamedQuery(name = "SubjectOutcome.findByIdSubjcet", query = "SELECT s FROM SubjectOutcome s WHERE s.idSubject = :idSubject"),
    @NamedQuery(name = "SubjectOutcome.findByIdSubjectOutcome", query = "SELECT s FROM SubjectOutcome s WHERE s.idSubjectOutcome = :idSubjectOutcome")})
public class SubjectOutcome implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subject_outcome")
    private Integer idSubjectOutcome;
    @Lob
    @Size(max = 65535)
    @Column(name = "outcome")
    private String outcome;
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    @ManyToOne(optional = false)
    private Subject idSubject;

    /**
     * Creates SubjectOutcome Entity
     */
    public SubjectOutcome() {
    }

    /**
     * Creates SubjectOutcome Entity with the specified 'id_subject_outcome'
     * @param idSubjectOutcome
     */
    public SubjectOutcome(Integer idSubjectOutcome) {
        this.idSubjectOutcome = idSubjectOutcome;
    }

    /**
     * Get id_subject_outcome from SubjectOutcome Entity
     * @return
     */
    public Integer getIdSubjectOutcome() {
        return idSubjectOutcome;
    }

    /**
     * Set id_subject_outcome for SubjectOutcome Entity
     * @param idSubjectOutcome
     */
    public void setIdSubjectOutcome(Integer idSubjectOutcome) {
        this.idSubjectOutcome = idSubjectOutcome;
    }

    /**
     * Get outcome from SubjectOutcome Entity
     * @return
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     * Set outcome for SubjectOutcome Entity
     * @param outcome
     */
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    /**
     * Get id_subject from SubjectOutcome Entity
     * @return
     */
    public Subject getIdSubject() {
        return idSubject;
    }

    /**
     * Set id_subject for SubjectOutcome Entity
     * @param idSubject
     */
    public void setIdSubject(Subject idSubject) {
        this.idSubject = idSubject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubjectOutcome != null ? idSubjectOutcome.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectOutcome)) {
            return false;
        }
        SubjectOutcome other = (SubjectOutcome) object;
        if ((this.idSubjectOutcome == null && other.idSubjectOutcome != null) || (this.idSubjectOutcome != null && !this.idSubjectOutcome.equals(other.idSubjectOutcome))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SubjectOutcome[ idSubjectOutcome=" + idSubjectOutcome + " ]";
    }
    
}
