package com.example.library.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "AUTHOR")
public class AuthorEntity {
    private int authorId;
    private String firstName;
    private String surname;
    private Date birthDay;
    private Collection<OwnershipEntity> ownerships;

    @Id
    @Column(name = "AUTHOR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "BIRTH_DAY")
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
        return birthDay != null ? birthDay.equals(that.birthDay) : that.birthDay == null;
    }

    @Override
    public int hashCode() {
        int result = authorId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "author")
    public Collection<OwnershipEntity> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(Collection<OwnershipEntity> ownerships) {
        this.ownerships = ownerships;
    }
}