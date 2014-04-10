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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creates POJO Entity for table 'subject_book'
 * @author piit
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

    /**
     * Creates SubjectBook Entity
     */
    public SubjectBook() {
    }

    /**
     * creates SubjectBook Entity with the specified 'id_subject_books'
     * @param idSubjectBooks
     */
    public SubjectBook(Integer idSubjectBooks) {
        this.idSubjectBooks = idSubjectBooks;
    }

    /**
     * Creates SubjectBook Entity with the specific 'id_subject_books' and 'text_reference'
     * @param idSubjectBooks
     * @param textReference
     */
    public SubjectBook(Integer idSubjectBooks, String textReference) {
        this.idSubjectBooks = idSubjectBooks;
        this.textReference = textReference;
    }

    /**
     * Get id_subject_books from  SubjectBook Entity
     * @return
     */
    public Integer getIdSubjectBooks() {
        return idSubjectBooks;
    }

    /**
     * Set id_subject_books for SubjectBook Entity
     * @param idSubjectBooks
     */
    public void setIdSubjectBooks(Integer idSubjectBooks) {
        this.idSubjectBooks = idSubjectBooks;
    }

    /**
     * Get text_reference from SubjectBook Entity
     * @return
     */
    public String getTextReference() {
        return textReference;
    }

    /**
     * Set text_reference for SubjectBook Entity
     * @param textReference
     */
    public void setTextReference(String textReference) {
        this.textReference = textReference;
    }

    /**
     * Get book_title from SubjectBook Entity
     * @return
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * Set book_title for SubjectBook Entity
     * @param bookTitle
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    /**
     * Get book_authors from SubjectBook Entity
     * @return
     */
    public String getBookAuthors() {
        return bookAuthors;
    }

    /**
     * Set book_authors for SubjectBook Entity
     * @param bookAuthors
     */
    public void setBookAuthors(String bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    /**
     * Get book_publisher from SubjectBook Entity
     * @return
     */
    public String getBookPublisher() {
        return bookPublisher;
    }

    /**
     * Set book_publisher for SubjectBook Entity
     * @param bookPublisher
     */
    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    /**
     * Get id_subject from SubjectBook Entity
     * @return
     */
    public Subject getIdSubject() {
        return idSubject;
    }

    /**
     * Set id_subject for SubjectBook Entity
     * @param idSubject
     */
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
