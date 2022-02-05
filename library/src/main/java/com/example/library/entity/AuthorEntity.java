package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "AUTHOR")
public class AuthorEntity {
    private int authorId;
    private String firstName;
    private String surname;
    private Date birthDay;

    @ManyToMany(mappedBy = "authors")
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
    private Collection<BookEntity> books = new ArrayList<>();

    @Id
    @Column(name = "AUTHOR_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "FIRST_NAME", nullable = true, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "SURNAME", nullable = false, length = 50)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "BIRTH_DAY", nullable = true)
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorEntity that = (AuthorEntity) o;

        if (authorId != that.authorId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (birthDay != null ? !birthDay.equals(that.birthDay) : that.birthDay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = authorId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    public Collection<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Collection<BookEntity> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
                "authorId=" + authorId +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
