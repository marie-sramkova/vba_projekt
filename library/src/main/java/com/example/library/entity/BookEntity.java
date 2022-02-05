package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "BOOK")
public class BookEntity {
    private int isbn;
    private String name;
    private Integer numberOfPages;
    private String genre;
    private String content;
    private String imageUrl;
    private String binding;
    private Collection<EnrollmentEntity> enrollments;


    @ManyToMany
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ISBN"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private Collection<AuthorEntity> authors = new ArrayList<>();

    public BookEntity() {
    }

    public BookEntity(int isbn, String name, Integer numberOfPages, String genre, String content, String imageUrl, String binding) {
        this.isbn = isbn;
        this.name = name;
        this.numberOfPages = numberOfPages;
        this.genre = genre;
        this.content = content;
        this.imageUrl = imageUrl;
        this.binding = binding;
    }

    @Id
    @Column(name = "ISBN", nullable = false)
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "NUMBER_OF_PAGES", nullable = true)
    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Basic
    @Column(name = "GENRE", nullable = true, length = 50)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Basic
    @Column(name = "CONTENT", nullable = true, length = 1000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "IMAGE_URL", nullable = true, length = 20000)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "BINDING", nullable = true, length = 50)
    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookEntity book = (BookEntity) o;

        if (isbn != book.isbn) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (numberOfPages != null ? !numberOfPages.equals(book.numberOfPages) : book.numberOfPages != null)
            return false;
        if (genre != null ? !genre.equals(book.genre) : book.genre != null) return false;
        if (content != null ? !content.equals(book.content) : book.content != null) return false;
        if (imageUrl != null ? !imageUrl.equals(book.imageUrl) : book.imageUrl != null) return false;
        if (binding != null ? !binding.equals(book.binding) : book.binding != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (numberOfPages != null ? numberOfPages.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (binding != null ? binding.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    public Collection<EnrollmentEntity> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Collection<EnrollmentEntity> enrollments) {
        this.enrollments = enrollments;
    }

    @ManyToMany
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ISBN"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    public Collection<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<AuthorEntity> authors) {
        this.authors = authors;
    }

    //    @OneToMany(mappedBy = "book")
//    @JsonIgnore
//    public Collection<OwnershipEntity> getOwnerships() {
//        return ownerships;
//    }
//
//    public void setOwnerships(Collection<OwnershipEntity> ownerships) {
//        this.ownerships = ownerships;
//    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "isbn=" + isbn +
                ", name='" + name + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", genre='" + genre + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", binding='" + binding + '\'' +
                '}';
    }

    public void addAuthor(AuthorEntity author) {
        authors.add(author);
    }
}
