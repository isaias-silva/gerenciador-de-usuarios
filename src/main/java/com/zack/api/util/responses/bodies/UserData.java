package com.zack.api.util.responses.bodies;

import com.zack.api.models.UserModel;
import com.zack.api.roles.UserRole;

public class UserData{
   private String name;
    private String profile;
   private String mail;
   private UserRole role;
   private String resume;

    public UserData(UserModel user) {
        this.name=user.getName();
        this.profile=user.getProfile();
        this.mail=user.getMail();
        this.role=user.getRole();
        this.resume=user.getResume();


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
}
