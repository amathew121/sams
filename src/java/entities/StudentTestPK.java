/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author piit
 */
@Embeddable
public class StudentTestPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_current_student")
    private int idCurrentStudent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_subject")
    private int idSubject;
    @Basic(optional = false)
    @NotNull
    @Column(name = "test")
    private short test;

    public StudentTestPK() {
    }

    public StudentTestPK(int idCurrentStudent, int idSubject, short test) {
        this.idCurrentStudent = idCurrentStudent;
        this.idSubject = idSubject;
        this.test = test;
    }

    public int getIdCurrentStudent() {
        return idCurrentStudent;
    }

    public void setIdCurrentStudent(int idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public short getTest() {
        return test;
    }

    public void setTest(short test) {
        this.test = test;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCurrentStudent;
        hash += (int) idSubject;
        hash += (int) test;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentTestPK)) {
            return false;
        }
        StudentTestPK other = (StudentTestPK) object;
        if (this.idCurrentStudent != other.idCurrentStudent) {
            return false;
        }
        if (this.idSubject != other.idSubject) {
            return false;
        }
        if (this.test != other.test) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StudentTestPK[ idCurrentStudent=" + idCurrentStudent + ", idSubject=" + idSubject + ", test=" + test + " ]";
    }
    
}
