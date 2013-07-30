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
 *
 * @author piit
 */
@Entity
@Table(name = "suggesstions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suggesstions.findAll", query = "SELECT s FROM Suggesstions s"),
    @NamedQuery(name = "Suggesstions.findByIdSuggesstions", query = "SELECT s FROM Suggesstions s WHERE s.idSuggesstions = :idSuggesstions")})
public class Suggesstions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_suggesstions")
    private Integer idSuggesstions;
    @Lob
    @Size(max = 16777215)
    @Column(name = "ss")
    private String ss;
    @JoinColumn(name = "id_faculty", referencedColumnName = "id_faculty")
    @ManyToOne(optional = false)
    private Faculty idFaculty;

    public Suggesstions() {
    }

    public Suggesstions(Integer idSuggesstions) {
        this.idSuggesstions = idSuggesstions;
    }

    public Integer getIdSuggesstions() {
        return idSuggesstions;
    }

    public void setIdSuggesstions(Integer idSuggesstions) {
        this.idSuggesstions = idSuggesstions;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
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
        hash += (idSuggesstions != null ? idSuggesstions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suggesstions)) {
            return false;
        }
        Suggesstions other = (Suggesstions) object;
        if ((this.idSuggesstions == null && other.idSuggesstions != null) || (this.idSuggesstions != null && !this.idSuggesstions.equals(other.idSuggesstions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Suggesstions[ idSuggesstions=" + idSuggesstions + " ]";
    }
    
}
