/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.feedback;

import entities.subject.Program;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ashish Mathew
 */
@Entity
@Table(name = "feedback2013_question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback2013Question.findAll", query = "SELECT f FROM Feedback2013Question f"),
    @NamedQuery(name = "Feedback2013Question.findByQid", query = "SELECT f FROM Feedback2013Question f WHERE f.qid = :qid"),
    @NamedQuery(name = "Feedback2013Question.findByQtype", query = "SELECT f FROM Feedback2013Question f WHERE f.qtype = :qtype"),
    @NamedQuery(name = "Feedback2013Question.findByQno", query = "SELECT f FROM Feedback2013Question f WHERE f.qno = :qno"),
    @NamedQuery(name = "Feedback2013Question.findByQtext", query = "SELECT f FROM Feedback2013Question f WHERE f.qtext = :qtext")})
public class Feedback2013Question implements Serializable {
    @JoinColumn(name = "id_program", referencedColumnName = "id_program")
    @ManyToOne
    private Program idProgram;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "qid")
    private Integer qid;
    @Column(name = "qtype")
    private Short qtype;
    @Column(name = "qno")
    private Integer qno;
    @Size(max = 300)
    @Column(name = "qtext")
    private String qtext;
    @OneToMany(mappedBy = "qid")
    private List<Feedback2013> feedback2013List;

    public Feedback2013Question() {
    }

    public Feedback2013Question(Integer qid) {
        this.qid = qid;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Short getQtype() {
        return qtype;
    }

    public void setQtype(Short qtype) {
        this.qtype = qtype;
    }

    public Integer getQno() {
        return qno;
    }

    public void setQno(Integer qno) {
        this.qno = qno;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String qtext) {
        this.qtext = qtext;
    }

    @XmlTransient
    public List<Feedback2013> getFeedback2013List() {
        return feedback2013List;
    }

    public void setFeedback2013List(List<Feedback2013> feedback2013List) {
        this.feedback2013List = feedback2013List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qid != null ? qid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback2013Question)) {
            return false;
        }
        Feedback2013Question other = (Feedback2013Question) object;
        if ((this.qid == null && other.qid != null) || (this.qid != null && !this.qid.equals(other.qid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" +idProgram+"]"+(qtype==0?"Theory":"Pracs")+" "+qno+"."+qtext;
    }

    public Program getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Program idProgram) {
        this.idProgram = idProgram;
    }
    
}
