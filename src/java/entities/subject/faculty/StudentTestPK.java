/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ashish Mathew
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

    /**
     * Creates StudentTestPK Entity
     */
    public StudentTestPK() {
    }

    /**
     * Creates StudentTestPK Entity with the specified id_current_student, id_subject and test
     * @param idCurrentStudent
     * @param idSubject
     * @param test
     */
    public StudentTestPK(int idCurrentStudent, int idSubject, short test) {
        this.idCurrentStudent = idCurrentStudent;
        this.idSubject = idSubject;
        this.test = test;
    }

    /**
     * Get id_current_Student from StudentTestPK Entity
     * @return
     */
    public int getIdCurrentStudent() {
        return idCurrentStudent;
    }

    /**
     * Set id_current_student for StudentTestPK Entity
     * @param idCurrentStudent
     */
    public void setIdCurrentStudent(int idCurrentStudent) {
        this.idCurrentStudent = idCurrentStudent;
    }

    /**
     * Get id_subject from StudentTestPK Entity
     * @return
     */
    public int getIdSubject() {
        return idSubject;
    }

    /**
     * Set id_subject for StudentTestPK Entity
     * @param idSubject
     */
    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * Get test from StudentTestPK Entity
     * @return
     */
    public short getTest() {
        return test;
    }

    /**
     * Set test for StudentTestPK Entity
     * @param test
     */
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
