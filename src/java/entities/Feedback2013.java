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
@Table(name = "feedback2013")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback2013.findAll", query = "SELECT f FROM Feedback2013 f"),
    @NamedQuery(name = "Feedback2013.findById", query = "SELECT f FROM Feedback2013 f WHERE f.id = :id"),
    @NamedQuery(name = "Feedback2013.findByIdFacultySubject", query = "SELECT f FROM Feedback2013 f WHERE f.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "Feedback2013.findByIdAnswer", query = "SELECT f FROM Feedback2013 f WHERE f.idAnswer = :idAnswer")})
public class Feedback2013 implements Serializable {
    @JoinColumn(name = "id_faculty_subject", referencedColumnName = "id_faculty_subject")
    @ManyToOne
    private FacultySubject idFacultySubject;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_answer")
    private Short idAnswer;
    @JoinColumn(name = "qid", referencedColumnName = "qid")
    @ManyToOne
    private Feedback2013Question qid;
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private Feedback2013Student uid;

    public Feedback2013() {
    }

    public Feedback2013(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Short idAnswer) {
        this.idAnswer = idAnswer;
    }

    public Feedback2013Question getQid() {
        return qid;
    }

    public void setQid(Feedback2013Question qid) {
        this.qid = qid;
    }

    public Feedback2013Student getUid() {
        return uid;
    }

    public void setUid(Feedback2013Student uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback2013)) {
            return false;
        }
        Feedback2013 other = (Feedback2013) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Feedback2013[ id=" + id + " ]";
    }

    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(FacultySubject idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }
    
}
