package com.bodjo.main.objects;

public class User {
    private String username;
    private int isAdmin;
    private int place;
    private int registrationTimestamp;
    private String email;

    public User(String username, int isAdmin, int place, int registrationTimestamp, String email) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.place = place;
        this.registrationTimestamp = registrationTimestamp;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(int registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
