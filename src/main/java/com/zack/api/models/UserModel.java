package com.zack.api.models;


import com.zack.api.roles.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "USERS")

public class UserModel implements Serializable, UserDetails {
    private static final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID id;
    private String name;

    private String mail;
    private String profile;

    private String password;
    private String resume;
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else if(this.role==UserRole.USER) return List.of(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_VERIFY_MAIL"));
        else return List.of(new SimpleGrantedAuthority("ROLE_VERIFY_MAIL"));
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
    public void setRole(UserRole role){
        this.role=role;
    }
}
