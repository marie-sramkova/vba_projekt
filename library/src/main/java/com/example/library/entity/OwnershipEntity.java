package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "OWNERSHIP")
public class OwnershipEntity {
    private int ownershipId;
    private int bookIsbn;
    private int authorId;
    private BookEntity book;
    private AuthorEntity author;

    public OwnershipEntity() {
    }

    public OwnershipEntity(BookEntity book, AuthorEntity author) {
        this.book = book;
        this.author = author;
    }

    @Id
    @Column(name = "OWNERSHIP_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getOwnershipId() {
        return ownershipId;
    }

    public void setOwnershipId(int ownershipId) {
        this.ownershipId = ownershipId;
    }

//    @Basic
//    @Column(name = "BOOK_ISBN")
//    public int getBookIsbn() {
//        return bookIsbn;
//    }
//
//    public void setBookIsbn(int bookIsbn) {
//        this.bookIsbn = bookIsbn;
//    }
//
//    @Basic
//    @Column(name = "AUTHOR_ID")
//    public int getAuthorId() {
//        return authorId;
//    }
//
//    public void setAuthorId(int authorId) {
//        this.authorId = authorId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnershipEntity that = (OwnershipEntity) o;

        if (ownershipId != that.ownershipId) return false;
        if (bookIsbn != that.bookIsbn) return false;
        return authorId == that.authorId;
    }

    @Override
    public int hashCode() {
        int result = ownershipId;
        result = 31 * result + bookIsbn;
        result = 31 * result + authorId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "BOOK_ISBN", referencedColumnName = "ISBN", nullable = false)
    @JsonIgnore
    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID", nullable = false)
    @JsonIgnore
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "OwnershipEntity{" +
                "ownershipId=" + ownershipId +
                ", book=" + book.getIsbn() +
                ", author=" + author.getAuthorId() +
                '}';
    }
}
