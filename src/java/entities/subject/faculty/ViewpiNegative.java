/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piit
 */
@Entity
@Table(name = "view_pi_9_negative")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewpiNegative.findAll", query = "SELECT v FROM ViewpiNegative v"),
    @NamedQuery(name = "ViewpiNegative.findByIdFeedbackType", query = "SELECT v FROM ViewpiNegative v WHERE v.idFeedbackType = :idFeedbackType"),
    @NamedQuery(name = "ViewpiNegative.findByIdProgram", query = "SELECT v FROM ViewpiNegative v WHERE v.idProgram = :idProgram"),
    @NamedQuery(name = "ViewpiNegative.findByIdProgramCourse", query = "SELECT v FROM ViewpiNegative v, FacultySubjectView f WHERE v.idFacultySubject=f.idFacultySubject  ORDER BY v.pi,v.fsv.idFaculty"),
    @NamedQuery(name = "ViewpiNegative.findByIdProgramCourseTheory", query = "SELECT v FROM ViewpiNegative v, FacultySubjectView f WHERE v.idProgram = :program and v.idFacultySubject=f.idFacultySubject and f.idCourse=:course and v.fsv.batch=0 ORDER BY v.pi,v.fsv.idFaculty"),
    @NamedQuery(name = "ViewpiNegative.findByIdProgramCoursePractical", query = "SELECT v FROM ViewpiNegative v, FacultySubjectView f WHERE v.idProgram = :program and v.idFacultySubject=f.idFacultySubject and f.idCourse=:course and v.fsv.batch<>0 ORDER BY v.pi,v.fsv.idFaculty"),
    @NamedQuery(name = "ViewpiNegative.findByBatch", query = "SELECT v FROM ViewpiNegative v WHERE v.batch = :batch"),
    @NamedQuery(name = "ViewpiNegative.findByIdFacultySubject", query = "SELECT v FROM ViewpiNegative v WHERE v.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "ViewpiNegative.findByIdFaculty", query = "SELECT v FROM ViewpiNegative v, FacultySubjectView f WHERE v.idFacultySubject = f.idFacultySubject and f.idFaculty = :idFaculty"),
    @NamedQuery(name = "ViewpiNegative.findByAnsTotal", query = "SELECT v FROM ViewpiNegative v WHERE v.ansTotal = :ansTotal"),
    @NamedQuery(name = "ViewpiNegative.findByAnsValueTotal", query = "SELECT v FROM ViewpiNegative v WHERE v.ansValueTotal = :ansValueTotal"),
    @NamedQuery(name = "ViewpiNegative.findByPi", query = "SELECT v FROM ViewpiNegative v WHERE v.pi = :pi")})
public class ViewpiNegative implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id_feedback_type")
    private Integer idFeedbackType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_program")
    private String idProgram;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "batch")
    private String batch;
    @Id
    @Column(name = "id_faculty_subject")
    private Integer idFacultySubject;
    @Column(name = "ans_total")
    private BigInteger ansTotal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ans_value_total")
    private BigDecimal ansValueTotal;
    @Column(name = "pi")
    private BigDecimal pi;
    @PrimaryKeyJoinColumn(name = "id_faculty_subject", referencedColumnName = "id_faculty_subject")
    @OneToOne(optional = false)
    private FacultySubjectView fsv;

    public ViewpiNegative() {
    }

    public Integer getIdFeedbackType() {
        return idFeedbackType;
    }

    public void setIdFeedbackType(Integer idFeedbackType) {
        this.idFeedbackType = idFeedbackType;
    }

    public String getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getIdFacultySubject() {
        return idFacultySubject;
    }

    public void setIdFacultySubject(Integer idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }

    public BigInteger getAnsTotal() {
        return ansTotal;
    }

    public void setAnsTotal(BigInteger ansTotal) {
        this.ansTotal = ansTotal;
    }

    public BigDecimal getAnsValueTotal() {
        return ansValueTotal;
    }

    public void setAnsValueTotal(BigDecimal ansValueTotal) {
        this.ansValueTotal = ansValueTotal;
    }

    public BigDecimal getPi() {
        return pi;
    }

    public void setPi(BigDecimal pi) {
        this.pi = pi;
    }

    public FacultySubjectView getFsv() {
        return fsv;
    }

    public void setFsv(FacultySubjectView fsv) {
        this.fsv = fsv;
    }
}
