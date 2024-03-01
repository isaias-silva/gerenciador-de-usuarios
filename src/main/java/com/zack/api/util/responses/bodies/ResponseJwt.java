package com.zack.api.util.responses.bodies;

public class ResponseJwt extends Response {

    private String token;

    public ResponseJwt(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken() {
        this.token = token;
    }
}
