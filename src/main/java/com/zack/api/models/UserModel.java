package com.zack.api.models;


import com.zack.api.roles.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity()
@Table(name = "USERS")

public class UserModel implements Serializable, UserDetails {
    private static final long serialId = 3L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;
    @Column
    private String mail;
    @Column
    private String profile;
    @Column
    private String password;
    @Column
    private String resume;

    @Column
    private UserRole role;
    @Column
    private String githubURL;
    @Column
    private String instagramURL;
    @Column
    private String portfolioURL;
    @OneToMany(mappedBy = "userMail")
    private List<EmailModel> received_mails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch (this.role) {

            case ADMIN -> {
                return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
            }
            case USER -> {
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }
            default -> {
                return List.of(new SimpleGrantedAuthority("ROLE_VERIFY_MAIL"));

            }
        }

    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPortfolioURL() {
        return portfolioURL;
    }

    public void setPortfolioURL(String portfolioURL) {
        this.portfolioURL = portfolioURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }


    public List<EmailModel> getReceived_mail() {
        return received_mails;
    }


}
