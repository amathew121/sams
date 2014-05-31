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
@Table(name = "subject_book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubjectBook.findAll", query = "SELECT s FROM SubjectBook s"),
    @NamedQuery(name = "SubjectBook.findByIdSubjectBooks", query = "SELECT s FROM SubjectBook s WHERE s.idSubjectBooks = :idSubjectBooks"),
    @NamedQuery(name = "SubjectBook.findByTextReference", query = "SELECT s FROM SubjectBook s WHERE s.textReference = :textReference"),
    @NamedQuery(name = "SubjectBook.findByBookTitle", query = "SELECT s FROM SubjectBook s WHERE s.bookTitle = :bookTitle"),
    @NamedQuery(name = "SubjectBook.findByIdSubject", query = "SELECT s FROM SubjectBook s WHERE s.idSubject = :idSubject"),
    @NamedQuery(name = "SubjectBook.findByBookAuthors", query = "SELECT s FROM SubjectBook s WHERE s.bookAuthors = :bookAuthors"),
    @NamedQuery(name = "SubjectBook.findByBookPublisher", query = "SELECT s FROM SubjectBook s WHERE s.bookPublisher = :bookPublisher")})
public class SubjectBook implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subject_books")
    private Integer idSubjectBooks;
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "text_reference")
    private String textReference;
    @Size(max = 100)
    @Column(name = "book_title")
    private String bookTitle;
    @Size(max = 100)
    @Column(name = "book_authors")
    private String bookAuthors;
    @Size(max = 100)
    @Column(name = "book_publisher")
    private String bookPublisher;
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    @ManyToOne(optional = false)
    private Subject idSubject;

    public SubjectBook() {
    }

    public SubjectBook(Integer idSubjectBooks) {
        this.idSubjectBooks = idSubjectBooks;
    }

    public SubjectBook(Integer idSubjectBooks, String textReference) {
        this.idSubjectBooks = idSubjectBooks;
        this.textReference = textReference;
    }

    public Integer getIdSubjectBooks() {
        return idSubjectBooks;
    }

    public void setIdSubjectBooks(Integer idSubjectBooks) {
        this.idSubjectBooks = idSubjectBooks;
    }

    public String getTextReference() {
        return textReference;
    }

    public void setTextReference(String textReference) {
        this.textReference = textReference;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(String bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
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
        hash += (idSubjectBooks != null ? idSubjectBooks.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectBook)) {
            return false;
        }
        SubjectBook other = (SubjectBook) object;
        if ((this.idSubjectBooks == null && other.idSubjectBooks != null) || (this.idSubjectBooks != null && !this.idSubjectBooks.equals(other.idSubjectBooks))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SubjectBook[ idSubjectBooks=" + idSubjectBooks + " ]";
    }
    
}
