/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'subject_syllabus'
 * @author piit
 */
@Entity
@Table(name = "subject_syllabus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubjectSyllabus.findAll", query = "SELECT s FROM SubjectSyllabus s"),
    @NamedQuery(name = "SubjectSyllabus.findByIdSubjectSyllabus", query = "SELECT s FROM SubjectSyllabus s WHERE s.idSubjectSyllabus = :idSubjectSyllabus"),
    @NamedQuery(name = "SubjectSyllabus.findByIdSubject", query = "SELECT s FROM SubjectSyllabus s WHERE s.idSubject = :idSubject"),
    @NamedQuery(name = "SubjectSyllabus.findByModuleNo", query = "SELECT s FROM SubjectSyllabus s WHERE s.moduleNo = :moduleNo"),
    @NamedQuery(name = "SubjectSyllabus.findByNoOfHours", query = "SELECT s FROM SubjectSyllabus s WHERE s.noOfHours = :noOfHours")})
public class SubjectSyllabus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subject_syllabus")
    private Integer idSubjectSyllabus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "module_no")
    private short moduleNo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "topics")
    private String topics;
    @Column(name = "no_of_hours")
    private Short noOfHours;
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    @ManyToOne
    private Subject idSubject;

    /**
     * Creates SubjectSyllabus Entity
     */
    public SubjectSyllabus() {
    }

    /**
     * Creates SubjectSyllabus Entity with the specified id_subject_syllabus
     * @param idSubjectSyllabus
     */
    public SubjectSyllabus(Integer idSubjectSyllabus) {
        this.idSubjectSyllabus = idSubjectSyllabus;
    }

    /**
     * Creates SubjectSyllabus Entity with the specified id_subject_syllabus, module_no and topics
     * @param idSubjectSyllabus
     * @param moduleNo
     * @param topics
     */
    public SubjectSyllabus(Integer idSubjectSyllabus, short moduleNo, String topics) {
        this.idSubjectSyllabus = idSubjectSyllabus;
        this.moduleNo = moduleNo;
        this.topics = topics;
    }

    /**
     * Get id_subject_syllabus from SubjectSyllabus Entity
     * @return
     */
    public Integer getIdSubjectSyllabus() {
        return idSubjectSyllabus;
    }

    /**
     * Set id_subject_syllabus for SubjectSyllabus Entity
     * @param idSubjectSyllabus
     */
    public void setIdSubjectSyllabus(Integer idSubjectSyllabus) {
        this.idSubjectSyllabus = idSubjectSyllabus;
    }

    /**
     * Get module_no from SubjectSyllabus Entity
     * @return
     */
    public short getModuleNo() {
        return moduleNo;
    }

    /**
     * Set module_no for SubjectSyllabus Entity
     * @param moduleNo
     */
    public void setModuleNo(short moduleNo) {
        this.moduleNo = moduleNo;
    }

    /**
     * Get topics from SubjectSyllabus Entity
     * @return
     */
    public String getTopics() {
        return topics;
    }

    /**
     * Set topics for SubjectSyllabus Entity
     * @param topics
     */
    public void setTopics(String topics) {
        this.topics = topics;
    }

    /**
     * Get no_of_hours from SubjectSyllabus Entity
     * @return
     */
    public Short getNoOfHours() {
        return noOfHours;
    }

    /**
     * Set no_of_hours for SubjectSyllabus Entity
     * @param noOfHours
     */
    public void setNoOfHours(Short noOfHours) {
        this.noOfHours = noOfHours;
    }

    /**
     * Get id_subject from SubjectSyllabus Entity
     * @return
     */
    public Subject getIdSubject() {
        return idSubject;
    }

    /**
     * Set id_subject for SubjectSyllabus Entity
     * @param idSubject
     */
    public void setIdSubject(Subject idSubject) {
        this.idSubject = idSubject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubjectSyllabus != null ? idSubjectSyllabus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectSyllabus)) {
            return false;
        }
        SubjectSyllabus other = (SubjectSyllabus) object;
        if ((this.idSubjectSyllabus == null && other.idSubjectSyllabus != null) || (this.idSubjectSyllabus != null && !this.idSubjectSyllabus.equals(other.idSubjectSyllabus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SubjectSyllabus[ idSubjectSyllabus=" + idSubjectSyllabus + " ]";
    }
    
}
