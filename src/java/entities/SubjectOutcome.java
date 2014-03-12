/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
 *
 * @author phcoe
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

    public SubjectOutcome() {
    }

    public SubjectOutcome(Integer idSubjectOutcome) {
        this.idSubjectOutcome = idSubjectOutcome;
    }

    public Integer getIdSubjectOutcome() {
        return idSubjectOutcome;
    }

    public void setIdSubjectOutcome(Integer idSubjectOutcome) {
        this.idSubjectOutcome = idSubjectOutcome;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Subject getIdSubject() {
        return idSubject;
    }

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
