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
@Table(name = "subject_evaluation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubjectEvaluation.findAll", query = "SELECT s FROM SubjectEvaluation s"),
    @NamedQuery(name = "SubjectEvaluation.findByIdSubject", query = "SELECT s FROM SubjectEvaluation s WHERE s.idSubject = :idSubject"),
    @NamedQuery(name = "SubjectEvaluation.findByIdSubjectEvaluation", query = "SELECT s FROM SubjectEvaluation s WHERE s.idSubjectEvaluation = :idSubjectEvaluation")})
public class SubjectEvaluation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subject_evaluation")
    private Integer idSubjectEvaluation;
    @Lob
    @Size(max = 16777215)
    @Column(name = "evaluation")
    private String evaluation;
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    @ManyToOne
    private Subject idSubject;

    public SubjectEvaluation() {
    }

    public SubjectEvaluation(Integer idSubjectEvaluation) {
        this.idSubjectEvaluation = idSubjectEvaluation;
    }

    public Integer getIdSubjectEvaluation() {
        return idSubjectEvaluation;
    }

    public void setIdSubjectEvaluation(Integer idSubjectEvaluation) {
        this.idSubjectEvaluation = idSubjectEvaluation;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
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
        hash += (idSubjectEvaluation != null ? idSubjectEvaluation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectEvaluation)) {
            return false;
        }
        SubjectEvaluation other = (SubjectEvaluation) object;
        if ((this.idSubjectEvaluation == null && other.idSubjectEvaluation != null) || (this.idSubjectEvaluation != null && !this.idSubjectEvaluation.equals(other.idSubjectEvaluation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SubjectEvaluation[ idSubjectEvaluation=" + idSubjectEvaluation + " ]";
    }
    
}
