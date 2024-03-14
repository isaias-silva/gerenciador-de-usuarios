package com.zack.api.roles;

public enum UserRole {

    USER("user"),
    ADMIN("admin"),
   VERIFY_MAIL("verify_mail"),

    BANNED("banned");

    public String getRole() {
        return role;
    }

    void setRole(String role) {
        this.role = role;
    }

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
