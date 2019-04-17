package com.bodjo.main.objects;

public class DatabaseConfigModel {
    private String database_password;
    private String database_user;

    public DatabaseConfigModel(String database_password, String database_user) {
        this.database_password = database_password;
        this.database_user = database_user;
    }

    public String getDatabasePassword() {
        return database_password;
    }

    public void setDatabasePassword(String database_password) {
        this.database_password = database_password;
    }

    public String getDatabaseUser() {
        return database_user;
    }

    public void setDatabaseUser(String database_user) {
        this.database_user = database_user;
    }
}
