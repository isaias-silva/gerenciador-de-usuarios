package com.zack.api.util.enums;

public enum TypeUpdateUser {
    NAME("name"),
    EMAIL("mail"),
    RESUME("resume")
    ;
    private String type;

   TypeUpdateUser(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
