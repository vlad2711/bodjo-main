package com.bodjo.main.controllers;

import com.bodjo.main.database.DatabaseController;
import com.bodjo.main.objects.ResponseError;
import com.bodjo.main.objects.SignUpResponse;
import com.bodjo.main.objects.User;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class AccountController {

    public static DatabaseController databaseController;

    public AccountController(){
        try {
            databaseController = new DatabaseController();
            databaseController.createTokenTable();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException | NamingException e) {
            LoggerFactory.getLogger("Main").error("Exception", e);
        }
    }

    public String login(String nickname, String password){
        try {
            User user = databaseController.getUser(nickname, password);

            if(user != null){


                return databaseController.addToken(nickname);
            }

        } catch (SQLException e) {
            LoggerFactory.getLogger("Main").error("Exception", e);
        }
        return "";
    }

    public static boolean checkToken(String token){
        try {
            return databaseController.checkToken(token);
        } catch (SQLException e) {
            LoggerFactory.getLogger("Main").error("Exception", e);
            return false;
        }
    }

    public String signup(String nickname, String password, String email, Integer place){
        try {
            String salt = generateToken();
            int resultCode = databaseController.addUser(nickname, toSHA512(password, salt), 0, salt, email, place);
            if (resultCode == 200) {
                return new Gson().toJson(new SignUpResponse("ok", databaseController.addToken(nickname)));
            }

            if (resultCode == 300) {
                return new Gson().toJson(new ResponseError("err", resultCode, "SQLException"));
            }

            if (resultCode == 301) {
                return new Gson().toJson(new ResponseError("err", resultCode, "User already exists"));
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger("Main").error("Exception", e);
        }

        return "";
    }

    public static void logout(String token){
        try {
            databaseController.removeToken(token);
        } catch (SQLException e) {
            LoggerFactory.getLogger("Main").error("Exception", e);
        }
    }

    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }

    public static String toSHA512(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            LoggerFactory.getLogger("Main").error("Exception", e);
        }

        return generatedPassword;
    }
}
