package com.example.library.entity;

import javax.persistence.*;

@Entity
@Table(name = "ENROLLMENT")
public class EnrollmentEntity {
    private int enrollmentId;
    private int appUserName;
    private int bookIsbn;
    private AppuserEntity appuser;
    private BookEntity book;

    @Id
    @Column(name = "ENROLLMENT_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }


//    @Basic
//    @Column(name = "APP_USER_ID")
//    public int getAppUserId() {
//        return appUserId;
//    }
//
//    public void setAppUserId(int appUserId) {
//        this.appUserId = appUserId;
//    }
//
//    @Basic
//    @Column(name = "BOOK_ISBN")
//    public int getBookIsbn() {
//        return bookIsbn;
//    }
//
//    public void setBookIsbn(int bookIsbn) {
//        this.bookIsbn = bookIsbn;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnrollmentEntity that = (EnrollmentEntity) o;

        if (enrollmentId != that.enrollmentId) return false;
        if (appUserName != that.appUserName) return false;
        return bookIsbn == that.bookIsbn;
    }

    @Override
    public int hashCode() {
        int result = enrollmentId;
        result = 31 * result + appUserName;
        result = 31 * result + bookIsbn;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "APP_USER_NAME", referencedColumnName = "NAME", nullable = false)
    public AppuserEntity getAppuser() {
        return appuser;
    }

    public void setAppuser(AppuserEntity appuser) {
        this.appuser = appuser;
    }

    @ManyToOne
    @JoinColumn(name = "BOOK_ISBN", referencedColumnName = "ISBN", nullable = false)
    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "EnrollmentEntity{" +
                "enrollmentId=" + enrollmentId +
                ", appuser=" + appuser +
                ", book=" + book +
                '}';
    }
}
