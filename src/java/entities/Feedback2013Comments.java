/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'feedback2013_comments'
 * @author piit
 */
@Entity
@Table(name = "feedback2013_comments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback2013Comments.findAll", query = "SELECT f FROM Feedback2013Comments f"),
    @NamedQuery(name = "Feedback2013Comments.findByIdFacultySubject", query = "SELECT f FROM Feedback2013Comments f WHERE f.idFacultySubject = :idFacultySubject"),
    @NamedQuery(name = "Feedback2013Comments.findById", query = "SELECT f FROM Feedback2013Comments f WHERE f.id = :id")})
public class Feedback2013Comments implements Serializable {
    @JoinColumn(name = "id_faculty_subject", referencedColumnName = "id_faculty_subject")
    @ManyToOne
    private FacultySubject idFacultySubject;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Size(max = 65535)
    @Column(name = "comments")
    private String comments;
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @ManyToOne
    private Feedback2013Student uid;

    /**
     * Creates Feedback2013Comments Entity
     */
    public Feedback2013Comments() {
    }

    /**
     * Creates Feedback2013Comments Entity with the specified 'id'
     * @param id
     */
    public Feedback2013Comments(Integer id) {
        this.id = id;
    }

    /**
     * Get id from Feedback2013Comments Entity
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set id for Feedback2013Comments Entity
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get comment from Feedback2013Comments Entity
     * @return
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set comment for Feedback2013Comments Entity
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * get uid from Feedback2013Comments Entity
     * @return
     */
    public Feedback2013Student getUid() {
        return uid;
    }

    /**
     * Set uid for Feedback2013Comments Entity
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
        if (!(object instanceof Feedback2013Comments)) {
            return false;
        }
        Feedback2013Comments other = (Feedback2013Comments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Feedback2013Comments[ id=" + id + " ]";
    }

    /**
     * Get id_faculty_subject from Feedback2013Comments Entity
     * @return
     */
    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     * Set id_faculty_subject from Feedback2013Comments Entity
     * @param idFacultySubject
     */
    public void setIdFacultySubject(FacultySubject idFacultySubject) {
        this.idFacultySubject = idFacultySubject;
    }
    
}
