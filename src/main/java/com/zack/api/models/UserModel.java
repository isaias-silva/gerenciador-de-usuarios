package com.zack.api.models;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity()
@Table(name="USERS")

public class UserModel implements Serializable {
private static final long serialId=1L;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;


private String name;

private String mail;
private String profile;

private String password;
private String resume;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getProfile() {
        return profile;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
