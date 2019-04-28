package com.bodjo.main.database;

import com.bodjo.main.Utils.Utils;
import com.bodjo.main.controllers.AccountController;
import com.bodjo.main.objects.User;
import com.bodjo.main.objects.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;

public class DatabaseController {
    private Connection connection = connect();

    public DatabaseController() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NamingException {
    }

    private Connection connect() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        String url = "jdbc:mysql://localhost/vkramdb?useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true";
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        return DriverManager.getConnection(url, Utils.dbUser, Utils.dbPassword);
    }

    public int addUser(String nickname, String password, int isAdmin,String salt, String email, Integer place) throws SQLException {
        Statement statement = connection.createStatement();
        if(!statement.executeQuery("SELECT * FROM accounts WHERE nickname = " + "\"" + nickname + "\"" ).next()){
            try {
                statement.execute("INSERT INTO accounts (nickname, password, is_admin, salt, email, registration_timestamp, place) VALUES(" + "\"" + nickname + "\"" + "," + "\"" + password + "\"" + "," + isAdmin + "," + "\"" + salt + "\"" + "," + "\"" + email + "\"" + "," + new Date().getTime() / 1000 + "," + place + ")");
                return 200;
            } catch (SQLException e){
                LoggerFactory.getLogger("Main").error("Exception", e);
                return 300;
            }
        } else {
            return 301;
        }

    }

    public User getUser(String nickname, String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE nickname = " + "\"" + nickname + "\"" );


        HashMap result = resultSetToHashMap(resultSet);

        if(!result.isEmpty()) {
            if (AccountController.toSHA512(password, result.get("salt").toString()).equals(result.get("password").toString())) {
                return new User(result.get("nickname").toString(),
                        (Integer) result.get("is_admin"),
                        (Integer) result.get("place"),
                        Integer.parseInt(result.get("registration_timestamp").toString()),
                        result.get("email").toString());
            } else {
                return null;
            }
        }else {
            return null;
        }
    }

    private HashMap resultSetToHashMap(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        HashMap row = new HashMap();
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
        }
        return row;
    }

    public void removeToken(String token) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM tokens WHERE token = " +  "\"" + token + "\"");
    }

    public boolean checkToken(String token) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT* FROM tokens WHERE token = " +  "\"" + token + "\"");
        HashMap result = resultSetToHashMap(resultSet);
        LoggerFactory.getLogger("Main").debug(result.toString());
        return !result.isEmpty();
    }


    public String addToken(String username) throws SQLException {
        String token;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT* FROM tokens WHERE username = " +  "\"" + username + "\"");
        HashMap result = resultSetToHashMap(resultSet);
        if(result.isEmpty()){
            token = AccountController.generateToken();
            statement.execute("INSERT INTO tokens(token, username) VALUES(" + "\"" + token + "\""  + "," + "\"" + username + "\"" + ")" );
        } else {
            token = result.get("token").toString();
        }

        return token;
    }

    public String getUsernameFromToken(String token){
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT* FROM tokens WHERE token = " +  "\"" + token + "\"");
            HashMap result = resultSetToHashMap(resultSet);
            return result.get("username").toString();
        } catch (SQLException e) {
            LoggerFactory.getLogger("Main").error("Exception", e);
        }

        return null;
    }

    public User getUserFromToken(String token){
        Statement statement;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT* FROM tokens WHERE token = " +  "\"" + token + "\"");
            HashMap result = resultSetToHashMap(resultSet);
            String username = result.get("username").toString();
            ResultSet r = statement.executeQuery("SELECT * FROM accounts WHERE nickname = " + "\"" + username + "\"" );
            HashMap res = resultSetToHashMap(r);
            return new User(res.get("nickname").toString(),
                    (Integer) res.get("is_admin"),
                    (Integer) res.get("place"),
                    Integer.parseInt(res.get("registration_timestamp").toString()),
                    res.get("email").toString());
        } catch (SQLException e) {
            LoggerFactory.getLogger("Main").error("Exception", e);
        }

        return null;

    }
    /*public void deleteToken() throws SQLException {
    }*/
    public void createTokenTable() throws SQLException {
     //   deleteTokenTable();
       // Statement statement = connection.createStatement();
      //  statement.execute("CREATE TABLE `tokens` (`token` VARCHAR(16) NOT NULL,`username` VARCHAR(100) NOT NULL,PRIMARY KEY (`token`));");
    }

    public void deleteTokenTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE tokens");
    }
}
