package com.bodjo.main.objects;

public class UserModel {
    private String type;
    private String username;
    private String token;

    public UserModel(String type, String username, String token) {
        this.type = type;
        this.username = username;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
