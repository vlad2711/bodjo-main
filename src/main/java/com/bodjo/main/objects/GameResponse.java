package com.bodjo.main.objects;

public class GameResponse {
    private String status;
    private String gameSessionToken;
    private int port;

    public GameResponse(String status, String gameSessionToken, int port) {
        this.status = status;
        this.gameSessionToken = gameSessionToken;
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGameSessionToken() {
        return gameSessionToken;
    }

    public void setGameSessionToken(String gameSessionToken) {
        this.gameSessionToken = gameSessionToken;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
