package com.zack.api.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity()
@Table(name = "EMAILS")
public class EmailModel implements Serializable {
    private static final long serialId = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String fromMail;
    @Column
    private String toMail;
    @Column
    private String subject;
    @Column(columnDefinition="TEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = true)
    private UserModel userMail;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserModel getUserMail() {
        return userMail;
    }

    public void setUserMail(UserModel userMail) {
        this.userMail = userMail;
    }
}
