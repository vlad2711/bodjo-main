package com.bodjo.main.objects;

public class SignUpResponse {
    private String status;
    private String token;
    public SignUpResponse(String status, String token) {
        this.status = status;
        this.token = token;
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

    public String getStatus() {
        return status;
    }

    public void setResult(String status) {
        this.status = status;
    }
}
