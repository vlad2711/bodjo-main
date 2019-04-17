package com.bodjo.main.objects;

public class GamesModel {
    private String  path;
    private String[] games;

    public GamesModel(String path, String[] games) {
        this.path = path;
        this.games = games;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getGames() {
        return games;
    }

    public void setGames(String[] games) {
        this.games = games;
    }
}
