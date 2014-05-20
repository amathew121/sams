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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piit
 */
@Entity
@Table(name = "feedback_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FeedbackType.findAll", query = "SELECT f FROM FeedbackType f"),
    @NamedQuery(name = "FeedbackType.findByIdFeedbackType", query = "SELECT f FROM FeedbackType f WHERE f.idFeedbackType = :idFeedbackType"),
    @NamedQuery(name = "FeedbackType.findByDescr", query = "SELECT f FROM FeedbackType f WHERE f.descr = :descr"),
    @NamedQuery(name = "FeedbackType.findByYr", query = "SELECT f FROM FeedbackType f WHERE f.yr = :yr")})
public class FeedbackType implements Serializable {
    @Column(name = "even")
    private Boolean even;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_feedback_type")
    private Integer idFeedbackType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descr")
    private String descr;
    @Column(name = "yr")
    private Integer yr;

    public FeedbackType() {
    }

    public FeedbackType(Integer idFeedbackType) {
        this.idFeedbackType = idFeedbackType;
    }

    public FeedbackType(Integer idFeedbackType, String descr) {
        this.idFeedbackType = idFeedbackType;
        this.descr = descr;
    }

    public Integer getIdFeedbackType() {
        return idFeedbackType;
    }

    public void setIdFeedbackType(Integer idFeedbackType) {
        this.idFeedbackType = idFeedbackType;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getYr() {
        return yr;
    }

    public void setYr(Integer yr) {
        this.yr = yr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFeedbackType != null ? idFeedbackType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeedbackType)) {
            return false;
        }
        FeedbackType other = (FeedbackType) object;
        if ((this.idFeedbackType == null && other.idFeedbackType != null) || (this.idFeedbackType != null && !this.idFeedbackType.equals(other.idFeedbackType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FeedbackType[ idFeedbackType=" + idFeedbackType + " ]";
    }

    public Boolean getEven() {
        return even;
    }

    public void setEven(Boolean even) {
        this.even = even;
    }
    
}
