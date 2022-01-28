package com.example.library.entities;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "APPUSER")
public class AppuserEntity {
    private int appUserId;
    private String nickName;
    private byte[] passwordHash;
    private byte[] salt;

    @Id
    @Column(name = "APP_USER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    @Basic
    @Column(name = "NICK_NAME", nullable = false, length = 50)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "PASSWORD_HASH", nullable = false)
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Basic
    @Column(name = "SALT", nullable = false)
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppuserEntity that = (AppuserEntity) o;

        if (appUserId != that.appUserId) return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
        if (!Arrays.equals(passwordHash, that.passwordHash)) return false;
        if (!Arrays.equals(salt, that.salt)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appUserId;
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(passwordHash);
        result = 31 * result + Arrays.hashCode(salt);
        return result;
    }

    @Override
    public String toString() {
        return "AppuserEntity{" +
                "appUserId=" + appUserId +
                ", nickName='" + nickName + '\'' +
                ", passwordHash=" + Arrays.toString(passwordHash) +
                ", salt=" + Arrays.toString(salt) +
                '}';
    }
}
