/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author piit
 */
@Entity
@Table(name = "feedback2013_student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback2013Student.findAll", query = "SELECT f FROM Feedback2013Student f"),
    @NamedQuery(name = "Feedback2013Student.findByUid", query = "SELECT f FROM Feedback2013Student f WHERE f.uid = :uid"),
    @NamedQuery(name = "Feedback2013Student.findByPwd", query = "SELECT f FROM Feedback2013Student f WHERE f.pwd = :pwd"),
    @NamedQuery(name = "Feedback2013Student.findBySemester", query = "SELECT f FROM Feedback2013Student f WHERE f.semester = :semester"),
    @NamedQuery(name = "Feedback2013Student.findBySemDivBatch", query = "SELECT f FROM Feedback2013Student f WHERE f.semester = :semester AND f.division = :division AND f.programCourse = :programCourse"),
    @NamedQuery(name = "Feedback2013Student.findByDivision", query = "SELECT f FROM Feedback2013Student f WHERE f.division = :division"),
    @NamedQuery(name = "Feedback2013Student.findByBatch", query = "SELECT f FROM Feedback2013Student f WHERE f.batch = :batch"),
    @NamedQuery(name = "Feedback2013Student.findByLoginStatus", query = "SELECT f FROM Feedback2013Student f WHERE f.loginStatus = :loginStatus"),
    @NamedQuery(name = "Feedback2013Student.findByLoginTime", query = "SELECT f FROM Feedback2013Student f WHERE f.loginTime = :loginTime"),
    @NamedQuery(name = "Feedback2013Student.findByLogoutTime", query = "SELECT f FROM Feedback2013Student f WHERE f.logoutTime = :logoutTime"),
    @NamedQuery(name = "Feedback2013Student.findByIpAddress", query = "SELECT f FROM Feedback2013Student f WHERE f.ipAddress = :ipAddress")})
public class Feedback2013Student implements Serializable {
    @OneToMany(mappedBy = "uid")
    private List<Feedback2013> feedback2013List;
    @OneToMany(mappedBy = "uid")
    private List<Feedback2013Comments> feedback2013CommentsList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uid")
    private Integer uid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pwd")
    private String pwd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "semester")
    private short semester;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "division")
    private String division;
    @Column(name = "batch")
    private Short batch;
    @Column(name = "login_status")
    private Boolean loginStatus;
    @Column(name = "login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;
    @Column(name = "logout_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutTime;
    @Size(max = 20)
    @Column(name = "ip_address")
    private String ipAddress;
    @JoinColumns({
        @JoinColumn(name = "id_program", referencedColumnName = "id_program"),
        @JoinColumn(name = "id_course", referencedColumnName = "id_course")})
    @ManyToOne(optional = false)
    private ProgramCourse programCourse;
    
    
    public Feedback2013Student() {
    }

    public Feedback2013Student(Integer uid) {
        this.uid = uid;
    }

    public Feedback2013Student(Integer uid, String pwd, short semester, String division) {
        this.uid = uid;
        this.pwd = pwd;
        this.semester = semester;
        this.division = division;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public short getSemester() {
        return semester;
    }

    public void setSemester(short semester) {
        this.semester = semester;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Short getBatch() {
        return batch;
    }

    public void setBatch(Short batch) {
        this.batch = batch;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
        public ProgramCourse getProgramCourse() {
        return programCourse;
    }

    public void setProgramCourse(ProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback2013Student)) {
            return false;
        }
        Feedback2013Student other = (Feedback2013Student) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Feedback2013Student[ uid=" + uid + " ]";
    }

    @XmlTransient
    public List<Feedback2013> getFeedback2013List() {
        return feedback2013List;
    }

    public void setFeedback2013List(List<Feedback2013> feedback2013List) {
        this.feedback2013List = feedback2013List;
    }

    @XmlTransient
    public List<Feedback2013Comments> getFeedback2013CommentsList() {
        return feedback2013CommentsList;
    }

    public void setFeedback2013CommentsList(List<Feedback2013Comments> feedback2013CommentsList) {
        this.feedback2013CommentsList = feedback2013CommentsList;
    }
    
}
