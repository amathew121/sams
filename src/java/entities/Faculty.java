/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Ashish
 */
@Entity
@Table(name = "faculty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faculty.findAll", query = "SELECT f FROM Faculty f"),
    @NamedQuery(name = "Faculty.findByIdFaculty", query = "SELECT f FROM Faculty f WHERE f.idFaculty = :idFaculty"),
    @NamedQuery(name = "Faculty.findByFacultyFname", query = "SELECT f FROM Faculty f WHERE f.facultyFname = :facultyFname"),
    @NamedQuery(name = "Faculty.findByFacultyLname", query = "SELECT f FROM Faculty f WHERE f.facultyLname = :facultyLname"),
    @NamedQuery(name = "Faculty.findByFacultyMobile", query = "SELECT f FROM Faculty f WHERE f.facultyMobile = :facultyMobile"),
    @NamedQuery(name = "Faculty.findByFacultyEmail", query = "SELECT f FROM Faculty f WHERE f.facultyEmail = :facultyEmail"),
    @NamedQuery(name = "Faculty.findByFacultyPassword", query = "SELECT f FROM Faculty f WHERE f.facultyPassword = :facultyPassword"),
    @NamedQuery(name = "Faculty.findByFacultyDepartment", query = "SELECT f FROM Faculty f WHERE f.facultyDepartment = :facultyDepartment"),
    @NamedQuery(name = "Faculty.findByFacultyLastLogin", query = "SELECT f FROM Faculty f WHERE f.facultyLastLogin = :facultyLastLogin"),
    @NamedQuery(name = "Faculty.findByFacultyTitle", query = "SELECT f FROM Faculty f WHERE f.facultyTitle = :facultyTitle"),
    @NamedQuery(name = "Faculty.findByToken", query = "SELECT f FROM Faculty f WHERE f.oauthToken = :token"),
    @NamedQuery(name = "Faculty.findByFacultyShowFeedback", query = "SELECT f FROM Faculty f WHERE f.facultyShowFeedback = :facultyShowFeedback")})
public class Faculty implements Serializable {
    @Size(max = 255)
    @Column(name = "oauth_code")
    private String oauthCode;
    @Size(max = 255)
    @Column(name = "oauth_token")
    private String oauthToken;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reviewedBy")
    private List<ReviewComments> reviewCommentsList;
    @Column(name = "Feedback360_allowed")
    private Boolean feedback360allowed;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idFaculty")
    private Reviewer reviewer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFaculty")
    private Collection<Suggesstions> suggesstionsCollection;
    @ManyToMany(mappedBy = "facultyCollection")
    private Collection<Department> departmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faculty")
    private Collection<Coordinator> coordinatorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faculty")
    private Collection<UserGroup> userGroupCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_faculty")
    private String idFaculty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "faculty_fname")
    private String facultyFname;
    @Size(max = 45)
    @Column(name = "faculty_lname")
    private String facultyLname;
    @Size(max = 10)
    @Column(name = "faculty_mobile")
    private String facultyMobile;
    @Size(max = 45)
    @Column(name = "faculty_email")
    private String facultyEmail;
    @Size(max = 255)
    @Column(name = "faculty_password")
    private String facultyPassword;
    @Size(max = 45)
    @Column(name = "faculty_department")
    private String facultyDepartment;
    @Column(name = "faculty_last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facultyLastLogin;
    @Size(max = 45)
    @Column(name = "faculty_title")
    private String facultyTitle;
    @Column(name = "faculty_show_feedback")
    private Boolean facultyShowFeedback;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFaculty")
    private Collection<FacultySubject> facultySubjectCollection;

    public Faculty() {
    }

    public Faculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public Faculty(String idFaculty, String facultyFname) {
        this.idFaculty = idFaculty;
        this.facultyFname = facultyFname;
    }

    public String getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public String getFacultyFname() {
        return facultyFname;
    }

    public void setFacultyFname(String facultyFname) {
        this.facultyFname = facultyFname;
    }

    public String getFacultyLname() {
        return facultyLname;
    }

    public void setFacultyLname(String facultyLname) {
        this.facultyLname = facultyLname;
    }

    public String getFacultyMobile() {
        return facultyMobile;
    }

    public void setFacultyMobile(String facultyMobile) {
        this.facultyMobile = facultyMobile;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyPassword() {
        return facultyPassword;
    }

    public void setFacultyPassword(String facultyPassword) {
        final String hash = DigestUtils.sha256Hex(facultyPassword);
        System.out.println(hash);
        this.facultyPassword = hash;
       // this.facultyPassword = facultyPassword;
    }

    public String getFacultyDepartment() {
        return facultyDepartment;
    }

    public void setFacultyDepartment(String facultyDepartment) {
        this.facultyDepartment = facultyDepartment;
    }

    public Date getFacultyLastLogin() {
        return facultyLastLogin;
    }

    public void setFacultyLastLogin(Date facultyLastLogin) {
        this.facultyLastLogin = facultyLastLogin;
    }

    public String getFacultyTitle() {
        return facultyTitle;
    }

    public void setFacultyTitle(String facultyTitle) {
        this.facultyTitle = facultyTitle;
    }

    public Boolean getFacultyShowFeedback() {
        return facultyShowFeedback;
    }

    public void setFacultyShowFeedback(Boolean facultyShowFeedback) {
        this.facultyShowFeedback = facultyShowFeedback;
    }

    @XmlTransient
    public Collection<FacultySubject> getFacultySubjectCollection() {
        return facultySubjectCollection;
    }

    public void setFacultySubjectCollection(Collection<FacultySubject> facultySubjectCollection) {
        this.facultySubjectCollection = facultySubjectCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFaculty != null ? idFaculty.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faculty)) {
            return false;
        }
        Faculty other = (Faculty) object;
        if ((this.idFaculty == null && other.idFaculty != null) || (this.idFaculty != null && !this.idFaculty.equals(other.idFaculty))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return facultyFname + " " + facultyLname;
    }

    @XmlTransient
    public Collection<UserGroup> getUserGroupCollection() {
        return userGroupCollection;
    }

    public void setUserGroupCollection(Collection<UserGroup> userGroupCollection) {
        this.userGroupCollection = userGroupCollection;
    }

    @XmlTransient
    public Collection<Department> getDepartmentCollection() {
        return departmentCollection;
    }

    public void setDepartmentCollection(Collection<Department> departmentCollection) {
        this.departmentCollection = departmentCollection;
    }

    @XmlTransient
    public Collection<Coordinator> getCoordinatorCollection() {
        return coordinatorCollection;
    }

    public void setCoordinatorCollection(Collection<Coordinator> coordinatorCollection) {
        this.coordinatorCollection = coordinatorCollection;
    }

    @XmlTransient
    public Collection<Suggesstions> getSuggesstionsCollection() {
        return suggesstionsCollection;
    }

    public void setSuggesstionsCollection(Collection<Suggesstions> suggesstionsCollection) {
        this.suggesstionsCollection = suggesstionsCollection;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public Boolean getFeedback360allowed() {
        return feedback360allowed;
    }

    public void setFeedback360allowed(Boolean feedback360allowed) {
        this.feedback360allowed = feedback360allowed;
    }

    @XmlTransient
    public List<ReviewComments> getReviewCommentsList() {
        return reviewCommentsList;
    }

    public void setReviewCommentsList(List<ReviewComments> reviewCommentsList) {
        this.reviewCommentsList = reviewCommentsList;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthCode() {
        return oauthCode;
    }

    public void setOauthCode(String oauthCode) {
        this.oauthCode = oauthCode;
    }
    
}
