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
 *
 * @author Ashish
 */
@Entity
@Table(name = "faculty_subject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacultySubject.findAll", query = "SELECT f FROM FacultySubject f"),
    @NamedQuery(name = "FacultySubject.findBySemDivBatchSub", query = "SELECT f FROM FacultySubject f WHERE f.idSubject = :idSubject AND f.batch = :batch AND f.division = :division"),
    @NamedQuery(name = "FacultySubject.findBySemDivPC", query = "SELECT f FROM FacultySubject f WHERE f.idSubject.semester = :semester AND f.division = :division AND f.idSubject.programCourse = :programCourse"),
    @NamedQuery(name = "FacultySubject.findByIdFaculty", query = "SELECT f FROM FacultySubject f WHERE f.idFaculty = :idFaculty"),
    @NamedQuery(name = "FacultySubject.findByIdFacultySubject", query = "SELECT f FROM FacultySubject f WHERE f.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "FacultySubject.findByDivision", query = "SELECT f FROM FacultySubject f WHERE f.division = :division"),
    @NamedQuery(name = "FacultySubject.findByBatch", query = "SELECT f FROM FacultySubject f WHERE f.batch = :batch")})
public class FacultySubject implements Serializable {
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

    public FacultySubject() {
    }

    public FacultySubject(Integer idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public FacultySubject(Integer idFacultySubject, String division, short batch) {
        this.idFacultySubject = idFacultySubject;
        this.division = division;
        this.batch = batch;
    }

    public Integer getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(Integer idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public short getBatch() {
        return batch;
    }
    
    public String getBatchDetail() {
        if (batch == 0)
            return "Theory";
        else
            return "Batch "+batch;
    }

    public void setBatch(short batch) {
        this.batch = batch;
    }

    @XmlTransient
    public Collection<Lecture> getLectureCollection() {
        return lectureCollection;
    }

    public void setLectureCollection(Collection<Lecture> lectureCollection) {
        this.lectureCollection = lectureCollection;
    }

    @XmlTransient
    public Collection<TeachingPlan> getTeachingPlanCollection() {
        return teachingPlanCollection;
    }

    public void setTeachingPlanCollection(Collection<TeachingPlan> teachingPlanCollection) {
        this.teachingPlanCollection = teachingPlanCollection;
    }

    public Subject getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Subject idSubject) {
        this.idSubject = idSubject;
    }

    public Faculty getIdFaculty() {
        return idFaculty;
    }

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

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
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
