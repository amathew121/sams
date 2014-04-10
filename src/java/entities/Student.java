/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Creates POJO Entity for table 'student'
 * @author Ashish
 */
@Entity
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByAdmnNo", query = "SELECT s FROM Student s WHERE s.admnNo = :admnNo"),
    @NamedQuery(name = "Student.findByStudentName", query = "SELECT s FROM Student s WHERE s.studentName = :studentName"),
    @NamedQuery(name = "Student.findByGender", query = "SELECT s FROM Student s WHERE s.gender = :gender"),
    @NamedQuery(name = "Student.findByDob", query = "SELECT s FROM Student s WHERE s.dob = :dob"),
    @NamedQuery(name = "Student.findByPhone", query = "SELECT s FROM Student s WHERE s.phone = :phone"),
    @NamedQuery(name = "Student.findByFatherName", query = "SELECT s FROM Student s WHERE s.fatherName = :fatherName"),
    @NamedQuery(name = "Student.findByMotherName", query = "SELECT s FROM Student s WHERE s.motherName = :motherName"),
    @NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email"),
    @NamedQuery(name = "Student.findByCancelled", query = "SELECT s FROM Student s WHERE s.cancelled = :cancelled")})
public class Student implements Serializable {
    @OneToMany(mappedBy = "admnNo")
    private List<CurrentStudent> currentStudentList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "admn_no")
    private String admnNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "student_name")
    private String studentName;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "phone")
    private String phone;
    @Size(max = 45)
    @Column(name = "father_name")
    private String fatherName;
    @Size(max = 45)
    @Column(name = "mother_name")
    private String motherName;
    @Lob
    @Size(max = 65535)
    @Column(name = "address")
    private String address;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Column(name = "cancelled")
    private Boolean cancelled;
    @JoinColumn(name = "id_program", referencedColumnName = "id_program")
    @ManyToOne(optional = false)
    private Program idProgram;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "admnNo")
    private CurrentStudent currentStudent;

    /**
     * Creates Student Entity
     */
    public Student() {
    }

    /**
     * Creates Student Entity with the specified 'admnNo'
     * @param admnNo
     */
    public Student(String admnNo) {
        this.admnNo = admnNo;
    }

    /**
     * Creates Student Entity with the specified admnNo and studentName
     * @param admnNo
     * @param studentName
     */
    public Student(String admnNo, String studentName) {
        this.admnNo = admnNo;
        this.studentName = studentName;
    }

    /**
     * Get AdmnNo from Student Entity
     * @return
     */
    public String getAdmnNo() {
        return admnNo;
    }

    /**
     * Set admnNo for Student Entity
     * @param admnNo
     */
    public void setAdmnNo(String admnNo) {
        this.admnNo = admnNo;
    }

    /**
     * Get student_name from Student Entity
     * @return
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Set student_name for Student Entity
     * @param studentName
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Get gender from Student Entity
     * @return
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * Set gender for Student Entity
     * @param gender
     */
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    /**
     * Get dob from Student Entity
     * @return
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Set dob for Student Entity
     * @param dob
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Get phone from Student Entity
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone for Student Entity
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get father_name from Student Entity
     * @return
     */
    public String getFatherName() {
        return fatherName;
    }

    /**
     * Set father_name for Student Entity
     * @param fatherName
     */
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    /**
     * Get mother_name from Student Entity
     * @return
     */
    public String getMotherName() {
        return motherName;
    }

    /**
     * Set mother_name for Student Entity
     * @param motherName
     */
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    /**
     * Get address from Student Entity
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address for Student Entity
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get email from Student Entity
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email for Student Entity
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get cancelled from Student Entity
     * @return
     */
    public Boolean getCancelled() {
        return cancelled;
    }

    /**
     * Set cancelled for Student Entity
     * @param cancelled
     */
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get id_program from Student Entity
     * @return
     */
    public Program getIdProgram() {
        return idProgram;
    }

    /**
     * Set id_program for Student Entity
     * @param idProgram
     */
    public void setIdProgram(Program idProgram) {
        this.idProgram = idProgram;
    }

    /**
     * Get current_student from Student Entity
     * @return
     */
    public CurrentStudent getCurrentStudent() {
        return currentStudent;
    }

    /**
     * Set current_student for Student Entity
     * @param currentStudent
     */
    public void setCurrentStudent(CurrentStudent currentStudent) {
        this.currentStudent = currentStudent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (admnNo != null ? admnNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.admnNo == null && other.admnNo != null) || (this.admnNo != null && !this.admnNo.equals(other.admnNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Student[ admnNo=" + admnNo + " ]";
    }

    /**
     * Gets list of CurrentStudent Entities for the Student Entity as a foreign key
     * @return
     */
    @XmlTransient
    public List<CurrentStudent> getCurrentStudentList() {
        return currentStudentList;
    }

    /**
     * Sets list of CurrentStudent Entities for the Student Entity as a foreign key
     * @param currentStudentList
     */
    public void setCurrentStudentList(List<CurrentStudent> currentStudentList) {
        this.currentStudentList = currentStudentList;
    }
    
}
