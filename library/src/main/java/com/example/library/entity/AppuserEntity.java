package com.example.library.entity;

import javax.persistence.*;

@Entity
@Table(name = "APPUSER")
public class AppuserEntity {
    private String name;
    private String password;
    private String roles;

    public AppuserEntity() {
    }

    public AppuserEntity(String name, String password, String roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    @Id
    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "ROLES", nullable = false)
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppuserEntity that = (AppuserEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppuserEntity{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
