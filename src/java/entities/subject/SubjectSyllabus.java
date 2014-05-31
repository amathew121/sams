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
 *
 * @author Ashish Mathew
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

    public SubjectSyllabus() {
    }

    public SubjectSyllabus(Integer idSubjectSyllabus) {
        this.idSubjectSyllabus = idSubjectSyllabus;
    }

    public SubjectSyllabus(Integer idSubjectSyllabus, short moduleNo, String topics) {
        this.idSubjectSyllabus = idSubjectSyllabus;
        this.moduleNo = moduleNo;
        this.topics = topics;
    }

    public Integer getIdSubjectSyllabus() {
        return idSubjectSyllabus;
    }

    public void setIdSubjectSyllabus(Integer idSubjectSyllabus) {
        this.idSubjectSyllabus = idSubjectSyllabus;
    }

    public short getModuleNo() {
        return moduleNo;
    }

    public void setModuleNo(short moduleNo) {
        this.moduleNo = moduleNo;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public Short getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(Short noOfHours) {
        this.noOfHours = noOfHours;
    }

    public Subject getIdSubject() {
        return idSubject;
    }

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
