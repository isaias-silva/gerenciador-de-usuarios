package com.zack.api.util.responses.bodies;

import com.zack.api.models.UserModel;
import com.zack.api.roles.UserRole;

public class UserData {
    private String name;
    private String profile;
    private String mail;
    private UserRole role;
    private String resume;
    private String githubURL;
    private String instagramURL;
    private String portfolioURL;

    public UserData(UserModel user) {
        this.name = user.getName();
        this.profile = user.getProfile();
        this.mail = user.getMail();
        this.role = user.getRole();
        this.resume = user.getResume();
        this.githubURL = user.getGithubURL();
        this.portfolioURL = user.getPortfolioURL();
        this.instagramURL = user.getInstagramURL();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getPortfolioURL() {
        return portfolioURL;
    }

    public void setPortfolioURL(String portfolioURL) {
        this.portfolioURL = portfolioURL;
    }
}
