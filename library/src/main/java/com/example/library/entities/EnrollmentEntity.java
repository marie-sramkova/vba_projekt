package com.example.library.entities;

import javax.persistence.*;

@Entity
@Table(name = "Enrollment")
public class EnrollmentEntity {
    private int enrollment_id;
    private int appUserId;
    private int bookIsbn;
    private AppuserEntity appuser;
    private BookEntity book;

    @Id
    @Column(name = "ENROLLMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollmentId) {
        this.enrollment_id = enrollmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnrollmentEntity that = (EnrollmentEntity) o;

        if (enrollment_id != that.enrollment_id) return false;
        if (appUserId != that.appUserId) return false;
        return bookIsbn == that.bookIsbn;
    }

    @Override
    public int hashCode() {
        int result = enrollment_id;
        result = 31 * result + appUserId;
        result = 31 * result + bookIsbn;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "APP_USER_ID", referencedColumnName = "APP_USER_ID", nullable = false)
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
}
