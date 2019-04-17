package com.bodjo.main.objects;

public class CheckTokenResponse {
    private String status;
    private String token;
    private String username;

    public CheckTokenResponse(String status, String token, String username) {
        this.status = status;
        this.token = token;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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
}
