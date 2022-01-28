package com.example.library.entities;

import javax.persistence.*;

@Entity
@Table(name = "OWNERSHIP")
public class OwnershipEntity {
    private int ownershipId;
    private int bookIsbn;
    private int authorId;
    private BookEntity book;
    private AuthorEntity author;

    @Id
    @Column(name = "OWNERSHIP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getOwnershipId() {
        return ownershipId;
    }

    public void setOwnershipId(int ownershipId) {
        this.ownershipId = ownershipId;
    }

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
    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity bookByBookIsbn) {
        this.book = bookByBookIsbn;
    }

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID", nullable = false)
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }
}
