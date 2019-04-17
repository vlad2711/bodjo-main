package com.bodjo.main.objects;

public class LoginResponse {
    private String status;
    private String token;
    private String username;

    public LoginResponse(String status, String token, String username) {
        this.status = status;
        this.token = token;
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
