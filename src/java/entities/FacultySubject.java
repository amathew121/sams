/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creates POJO Entity for table 'faculty_subject'
 * @author Ashish
 */
@Entity
@Table(name = "faculty_subject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacultySubject.findAll", query = "SELECT f FROM FacultySubject f"),
    @NamedQuery(name = "FacultySubject.findBySemDivBatchSub", query = "SELECT f FROM FacultySubject f WHERE f.idSubject = :idSubject AND f.batch = :batch AND f.division = :division"),
    @NamedQuery(name = "FacultySubject.findBySemDivBatchSubEven", query = "SELECT f FROM FacultySubject f WHERE f.idSubject = :idSubject AND f.batch = :batch AND f.division = :division AND MOD(f.idSubject.semester,2)=0 "),
    @NamedQuery(name = "FacultySubject.findBySemDivPC", query = "SELECT f FROM FacultySubject f WHERE f.idSubject.semester = :semester AND f.division = :division AND f.idSubject.programCourse = :programCourse"),
    @NamedQuery(name = "FacultySubject.findByIdFaculty", query = "SELECT f FROM FacultySubject f WHERE f.idFaculty = :idFaculty"),
    @NamedQuery(name = "FacultySubject.findByIdSubject", query = "SELECT f FROM FacultySubject f WHERE f.idSubject = :idSubject"),
    @NamedQuery(name = "FacultySubject.findByIdFacultySubject", query = "SELECT f FROM FacultySubject f WHERE f.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "FacultySubject.findByDivision", query = "SELECT f FROM FacultySubject f WHERE f.division = :division"),
    @NamedQuery(name = "FacultySubject.findByBatch", query = "SELECT f FROM FacultySubject f WHERE f.batch = :batch")})
public class FacultySubject implements Serializable {
    @Lob
    @Size(max = 65535)
    @Column(name = "feedback_review")
    private String feedbackReview;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFacultySubject")
    private List<ReviewComments> reviewCommentsList;
    @OneToMany(mappedBy = "idFacultySubject")
    private List<Feedback2013Comments> feedback2013CommentsList;
    @OneToMany(mappedBy = "idFacultySubject")
    private List<Feedback2013> feedback2013List;
    @Column(name = "academic_year")
    private Integer academicYear;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_faculty_subject")
    private Integer idFacultySubject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "division")
    private String division;
    @Basic(optional = false)
    @NotNull
    @Column(name = "batch")
    private short batch;
    @OneToMany(mappedBy = "idFacultySubject")
    private Collection<Lecture> lectureCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFacultySubject")
    private Collection<TeachingPlan> teachingPlanCollection;
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    @ManyToOne(optional = false)
    private Subject idSubject;
    @JoinColumn(name = "id_faculty", referencedColumnName = "id_faculty")
    @ManyToOne(optional = false)
    private Faculty idFaculty;

    /**
     * Creates FacultySubject Entity
     */
    public FacultySubject() {
    }

    /**
     * Creates FacultySubject Entity with the specified id_faculty_subject
     * @param idFacultySubject
     */
    public FacultySubject(Integer idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    /**
     * Creates FacultySubject Entity with the specified id_faculty_subject, division and batch
     * @param idFacultySubject
     * @param division
     * @param batch
     */
    public FacultySubject(Integer idFacultySubject, String division, short batch) {
        this.idFacultySubject = idFacultySubject;
        this.division = division;
        this.batch = batch;
    }

    /**
     * Get id_faculty_Subject from FacultySubject Entity
     * @return
     */
    public Integer getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject for FacultySubject Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(Integer idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    /**
     * Get division from FacultySubject Entity
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division for FacultySubject Entity
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get batch from FacultySubject Entity
     * @return
     */
    public short getBatch() {
        return batch;
    }
    
    /**
     * Get batch_detail from FacultySubject Entity
     * @return
     */
    public String getBatchDetail() {
        if (batch == 0)
            return "Theory";
        else
            return "" + batch;
    }

    /**
     * Set batch for FacultySubject Entity
     * @param batch
     */
    public void setBatch(short batch) {
        this.batch = batch;
    }

    /**
     * Gets collection of Lecture Entities for the FacultySubject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<Lecture> getLectureCollection() {
        return lectureCollection;
    }

    /**
     * Sets collection of Lecture Entities for the FacultySubject Entity as a foreign key
     * @param lectureCollection
     */
    public void setLectureCollection(Collection<Lecture> lectureCollection) {
        this.lectureCollection = lectureCollection;
    }

    /**
     * Gets collection of TeachingPlan Entities for the FacultySubject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public Collection<TeachingPlan> getTeachingPlanCollection() {
        return teachingPlanCollection;
    }

    /**
     * Sets collection of TeachingPlan Entities for the FacultySubject Entity as a foreign key
     * @param teachingPlanCollection
     */
    public void setTeachingPlanCollection(Collection<TeachingPlan> teachingPlanCollection) {
        this.teachingPlanCollection = teachingPlanCollection;
    }

    /**
     * Get id_subject from FacultySubject Entity
     * @return
     */
    public Subject getIdSubject() {
        return idSubject;
    }

    /**
     * Set id_subject for FacultySubject Entity
     * @param idSubject
     */
    public void setIdSubject(Subject idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * Get id_faculty from FacultySubject Entity
     * @return
     */
    public Faculty getIdFaculty() {
        return idFaculty;
    }

    /**
     * Set id_faculty for FacultySubject Entity
     * @param idFaculty
     */
    public void setIdFaculty(Faculty idFaculty) {
        this.idFaculty = idFaculty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacultySubject != null ? idFacultySubject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacultySubject)) {
            return false;
        }
        FacultySubject other = (FacultySubject) object;
        if ((this.idFacultySubject == null && other.idFacultySubject != null) || (this.idFacultySubject != null && !this.idFacultySubject.equals(other.idFacultySubject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idSubject.toString()+"/"+division+"/"+batch;
    }

    /**
     * Get academic_year from FacultySubject Entity
     * @return
     */
    public Integer getAcademicYear() {
        return academicYear;
    }

    /**
     * Set academic_year from FacultySubject Entity
     * @param academicYear
     */
    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    /**
     * Gets list of Feedback2013 Entities for the FacultySubject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<Feedback2013> getFeedback2013List() {
        return feedback2013List;
    }

    /**
     * Sets list of Feedback2013 Entities for the FacultySubject Entity as a foreign key
     * @param feedback2013List
     */
    public void setFeedback2013List(List<Feedback2013> feedback2013List) {
        this.feedback2013List = feedback2013List;
    }

    /**
     * Gets list of Feedback2013Comments Entities for the FacultySubject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<Feedback2013Comments> getFeedback2013CommentsList() {
        return feedback2013CommentsList;
    }

    /**
     * Sets list of Feedback2013Comments Entities for the FacultySubject Entity as a foreign key
     * @param feedback2013CommentsList
     */
    public void setFeedback2013CommentsList(List<Feedback2013Comments> feedback2013CommentsList) {
        this.feedback2013CommentsList = feedback2013CommentsList;
    }

    /**
     * Get feedback_review from FacultySubject Entity
     * @return
     */
    public String getFeedbackReview() {
        return feedbackReview;
    }

    /**
     * Set feedback_review for FacultySubject Entity
     * @param feedbackReview
     */
    public void setFeedbackReview(String feedbackReview) {
        this.feedbackReview = feedbackReview;
    }

    /**
     * Gets list of ReviewComments Entities for the FacultySubject Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<ReviewComments> getReviewCommentsList() {
        return reviewCommentsList;
    }

    /**
     * Sets list of ReviewComments Entities for the FacultySubject Entity as a foreign key
     * @param reviewCommentsList
     */
    public void setReviewCommentsList(List<ReviewComments> reviewCommentsList) {
        this.reviewCommentsList = reviewCommentsList;
    }
    
}
