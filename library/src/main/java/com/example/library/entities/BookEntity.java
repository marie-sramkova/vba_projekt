package com.example.library.entities;

import javax.persistence.*;
import java.util.Collection;

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
    private Collection<OwnershipEntity> ownerships;

    @Id
    @Column(name = "ISBN")
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "NUMBER_OF_PAGES")
    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Basic
    @Column(name = "GENRE")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Basic
    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "IMAGE_URL")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "BINDING")
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

        BookEntity that = (BookEntity) o;

        if (isbn != that.isbn) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (numberOfPages != null ? !numberOfPages.equals(that.numberOfPages) : that.numberOfPages != null)
            return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        return binding != null ? binding.equals(that.binding) : that.binding == null;
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
    public Collection<EnrollmentEntity> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Collection<EnrollmentEntity> enrollments) {
        this.enrollments = enrollments;
    }

    @OneToMany(mappedBy = "book")
    public Collection<OwnershipEntity> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(Collection<OwnershipEntity> ownerships) {
        this.ownerships = ownerships;
    }

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
}
