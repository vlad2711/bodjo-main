package com.bodjo.main.controllers;

import com.bodjo.main.objects.GameResponse;

import java.util.HashMap;

public class GameController {
    private static HashMap<String, String> sessions = new HashMap<>();
    private String getGameSessionToken(String token){

        if(sessions.containsKey(token)){
            return sessions.get(token);
        }

        String sessionToken = AccountController.generateToken();
        sessions.put(token, sessionToken);
        return sessionToken;
    }

    private Integer getPort(String gameName){
        return ServerController.ports.get(gameName);
    }

    public GameResponse getGame(String token, String gameName){
        return new GameResponse("ok", getGameSessionToken(token), getPort(gameName));
    }
}
