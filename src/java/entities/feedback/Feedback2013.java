/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.feedback;

import entities.subject.faculty.FacultySubject;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO entity for table 'feedback2013'
 * @author piit
 */
@Entity
@Table(name = "feedback2013")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback2013.findAll", query = "SELECT f FROM Feedback2013 f"),
    @NamedQuery(name = "Feedback2013.findByQuestion", query = "SELECT f.idFacultySubject,f.idFeedbackType,f.idAnswer,count(f.idAnswer) FROM Feedback2013 f WHERE f.qid.qid = :qid AND f.idFeedbackType.idFeedbackType = :idFeedbackType GROUP BY f.idFacultySubject, f.idAnswer ORDER BY f.idFacultySubject.idFaculty DESC"),
    @NamedQuery(name = "Feedback2013.findById", query = "SELECT f FROM Feedback2013 f WHERE f.id = :id"),
    @NamedQuery(name = "Feedback2013.findByIdFacultySubject", query = "SELECT f FROM Feedback2013 f WHERE f.idFacultySubject = :idFacultySubject AND f.idFeedbackType = :fType"),
    @NamedQuery(name = "Feedback2013.findByRating", query = "SELECT f FROM Feedback2013 f WHERE f.idFacultySubject = :idFacultySubject GROUP BY f.qid"),
    @NamedQuery(name = "Feedback2013.findByIdAnswer", query = "SELECT f FROM Feedback2013 f WHERE f.idAnswer = :idAnswer")})
public class Feedback2013 implements Serializable {
    @JoinColumn(name = "id_feedback_type", referencedColumnName = "id_feedback_type")
    @ManyToOne(optional = false)
    private FeedbackType idFeedbackType;
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

    @Transient
    private int ra0;
    @Transient
    private int ra1;
    @Transient
    private int ra2;
    @Transient
    private int ra3;
    @Transient
    private int ra4;
    @Transient
    private int ra5;

    /**
     * TODO:
     * @return
     */
    public int getRa0() {
        return ra0;
    }

    /**
     * TODO:
     * @param ra0
     */
    public void setRa0(int ra0) {
        this.ra0 = ra0;
    }

    /**
     * TODO:
     * @return
     */
    public int getRa1() {
        return ra1;
    }

    /**
     * TODO:
     * @param ra1
     */
    public void setRa1(int ra1) {
        this.ra1 = ra1;
    }

    /**
     * TODO:
     * @return
     */
    public int getRa2() {
        return ra2;
    }

    /**
     * TODO:
     * @param ra2
     */
    public void setRa2(int ra2) {
        this.ra2 = ra2;
    }

    /**
     * TODO:
     * @return
     */
    public int getRa3() {
        return ra3;
    }

    /**
     * TODO:
     * @param ra3
     */
    public void setRa3(int ra3) {
        this.ra3 = ra3;
    }

    /**
     * TODO:
     * @return
     */
    public int getRa4() {
        return ra4;
    }

    /**
     * TODO:
     * @param ra4
     */
    public void setRa4(int ra4) {
        this.ra4 = ra4;
    }

    /**
     * TODO:
     * @return
     */
    public int getRa5() {
        return ra5;
    }

    /**
     * TODO:
     * @param ra5
     */
    public void setRa5(int ra5) {
        this.ra5 = ra5;
    }
    
    /**
     * Creates Feedback2013 Entity
     */
    public Feedback2013() {
    }

    /**
     * Creates Feedback2013 Entity with the specified 'id'
     * @param id
     */
    public Feedback2013(Long id) {
        this.id = id;
    }

    /**
     * Get id from Feedback2013 Entity
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id for Feedback2013 Entity
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get id_answer from Feedback2013 Entity
     * @return
     */
    public Short getIdAnswer() {
        return idAnswer;
    }

    /**
     * Set id_answer for Feedback2013 Entity
     * @param idAnswer
     */
    public void setIdAnswer(Short idAnswer) {
        this.idAnswer = idAnswer;
    }

    /**
     * Get qid from Feedback2013 Entity
     * @return
     */
    public Feedback2013Question getQid() {
        return qid;
    }

    /**
     * Set qid for Feedback2013 Entity
     * @param qid
     */
    public void setQid(Feedback2013Question qid) {
        this.qid = qid;
    }

    /**
     * Get uid from Feedback2013 Entity
     * @return
     */
    public Feedback2013Student getUid() {
        return uid;
    }

    /**
     * Set uid for Feedback2013 Entity
     * @param uid
     */
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

    /**
     * Get id_faculty_subject from Feedback2013 Entity
     * @return
     */
    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject from Feedback2013 Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(FacultySubject idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public FeedbackType getIdFeedbackType() {
        return idFeedbackType;
    }

    public void setIdFeedbackType(FeedbackType idFeedbackType) {
        this.idFeedbackType = idFeedbackType;
    }
    
}
