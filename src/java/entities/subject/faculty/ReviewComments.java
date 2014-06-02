/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty;

import entities.subject.faculty.FacultySubject;
import entities.users.Faculty;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'review_comments'
 * @author piit
 */
@Entity
@Table(name = "review_comments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReviewComments.findAll", query = "SELECT r FROM ReviewComments r"),
    @NamedQuery(name = "ReviewComments.findByIdReviewComments", query = "SELECT r FROM ReviewComments r WHERE r.idReviewComments = :idReviewComments"),
    @NamedQuery(name = "ReviewComments.findByReviewedOn", query = "SELECT r FROM ReviewComments r WHERE r.reviewedOn = :reviewedOn"),
    @NamedQuery(name = "ReviewComments.findByIdFacSubType", query = "SELECT r FROM ReviewComments r WHERE r.reviewType = :reviewType AND r.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "ReviewComments.findByReviewType", query = "SELECT r FROM ReviewComments r WHERE r.reviewType = :reviewType")})
public class ReviewComments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_review_comments")
    private Integer idReviewComments;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "comment")
    private String comment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reviewed_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewedOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "review_type")
    private short reviewType;
    @JoinColumn(name = "id_faculty_subject", referencedColumnName = "id_faculty_subject")
    @ManyToOne(optional = false)
    private FacultySubject idFacultySubject;
    @JoinColumn(name = "reviewed_by", referencedColumnName = "id_faculty")
    @ManyToOne(optional = false)
    private Faculty reviewedBy;

    /**
     * Creates ReviewComments Entity
     */
    public ReviewComments() {
    }

    /**
     * Creates ReviewComments Entity with the specified 'id_review_comments'
     * @param idReviewComments
     */
    public ReviewComments(Integer idReviewComments) {
        this.idReviewComments = idReviewComments;
    }

    /**
     * Creates ReviewComments Entity with the specified id_review_comments, comment, reviewed_on and review_type 
     * @param idReviewComments
     * @param comment
     * @param reviewedOn
     * @param reviewType
     */
    public ReviewComments(Integer idReviewComments, String comment, Date reviewedOn, short reviewType) {
        this.idReviewComments = idReviewComments;
        this.comment = comment;
        this.reviewedOn = reviewedOn;
        this.reviewType = reviewType;
    }

    /**
     * Get id_review_comments from ReviewComments Entity 
     * @return
     */
    public Integer getIdReviewComments() {
        return idReviewComments;
    }

    /**
     * Set id_review_comments for ReviewComments Entity
     * @param idReviewComments
     */
    public void setIdReviewComments(Integer idReviewComments) {
        this.idReviewComments = idReviewComments;
    }

    /**
     * Get comment from ReviewComments Entity 
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment for ReviewComments Entity
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get reviewed_on from ReviewComments Entity
     * @return
     */
    public Date getReviewedOn() {
        return reviewedOn;
    }

    /**
     * Set reviewed_on for ReviewComments Entity
     * @param reviewedOn
     */
    public void setReviewedOn(Date reviewedOn) {
        this.reviewedOn = reviewedOn;
    }

    /**
     * Get review_type from ReviewComments Entity
     * @return
     */
    public short getReviewType() {
        return reviewType;
    }

    /**
     * Set review_type for ReviewComments Entity
     * @param reviewType
     */
    public void setReviewType(short reviewType) {
        this.reviewType = reviewType;
    }

    /**
     * Get id_faculty_subject from ReviewComments Entity
     * @return
     */
    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject for ReviewComments Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(FacultySubject idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    /**
     * Get reviewed_by from ReviewComments Entity
     * @return
     */
    public Faculty getReviewedBy() {
        return reviewedBy;
    }

    /**
     * Set reviewed_by for ReviewComments Entity
     * @param reviewedBy
     */
    public void setReviewedBy(Faculty reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReviewComments != null ? idReviewComments.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReviewComments)) {
            return false;
        }
        ReviewComments other = (ReviewComments) object;
        if ((this.idReviewComments == null && other.idReviewComments != null) || (this.idReviewComments != null && !this.idReviewComments.equals(other.idReviewComments))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ReviewComments[ idReviewComments=" + idReviewComments + " ]";
    }
    
}
