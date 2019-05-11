package com.bodjo.main.web;

import com.bodjo.main.Utils.Utils;
import com.bodjo.main.controllers.AccountController;
import com.bodjo.main.controllers.GameController;
import com.bodjo.main.controllers.ServerController;
import com.bodjo.main.objects.*;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class RESTController {
    private AccountController accountController = new AccountController();
    private GameController gameController = new GameController();

    @PostMapping("/register")
    String register(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("email") String email,
                            @RequestParam("place") Integer place,
                            HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");

        if(username == null || password == null || username.length() < 4 || password.length() < 6){
            return new Gson().toJson(new ResponseError("err", 400, "Too short login or password"));
        }

        if(!Utils.checkValid(username)){
            return new Gson().toJson(new ResponseError("err", 400, "Bad username"));
        }

        if(password.length() < 100 && username.length() < 100) {
            return accountController.signup(username, password, email, place);
        } else {
            return new Gson().toJson(new ResponseError("err", 400, "To Long password or nickname"));
        }
    }

    @PostMapping("/login")
    String login(@RequestParam("username") String username,
                 @RequestParam("password") String password,
                 HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");

        String token = accountController.login(username, password);

        if(token.equals("")){
            return new Gson().toJson(new ResponseError("err", 401, "Wrong nickname or password"));
        }

        return new Gson().toJson(new LoginResponse("ok", token, username));
    }

    @GetMapping("/play")
    String play(@RequestParam("token") String token,
                @RequestParam("gameName") String gameName,
                HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        if(AccountController.checkToken(token)) {
            GameResponse gameResponse = gameController.getGame(token, gameName);
            ServerController.addUser(gameName, AccountController.databaseController.getUsernameFromToken(token), gameResponse.getGameSessionToken());

            return new Gson().toJson(gameResponse);
        } else return new Gson().toJson(new ResponseError("err", 401,"Unauthorized"));
    }

    @GetMapping("/check_token")
    public String checkToken(@RequestParam("token") String token,
                             HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");

        if(AccountController.checkToken(token)) {
            return new Gson().toJson(new CheckTokenResponse("ok", token, AccountController.databaseController.getUsernameFromToken(token)));
        }
        return new Gson().toJson(new ResponseError("err", 401,"Unauthorized"));
    }

    @GetMapping("/services")
    public String getServices(HttpServletResponse response){
        Services services = new Services(new ArrayList<>());

        for (int i = 0; i < ServerController.serversNames.size(); i++) {
            if(ServerController.ports.containsKey(ServerController.serversNames.get(i))) {
                services.getServices().add(new Service(ServerController.serversNames.get(i), ServerController.ports.get(ServerController.serversNames.get(i))));
            } else {
                services.getServices().add(new Service(ServerController.serversNames.get(i), ServerController.tcpServers.get(ServerController.serversNames.get(i)).port));
            }
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        return new Gson().toJson(services);
    }

    @GetMapping("/info")
    public String getInfo(@RequestParam("token") String token,
                          HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");

        if(AccountController.checkToken(token)) {
            return new Gson().toJson(AccountController.databaseController.getUserFromToken(token));
        }

        return new Gson().toJson(new ResponseError("err", 401,"Unauthorized"));
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(HttpServletRequest httpRequest, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }

        return new Gson().toJson(new ResponseError("err", httpErrorCode, errorMsg));
    }


    @PostMapping("/log_out")
    public String logOut(@RequestParam("token") String token,
                         HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        AccountController.logout(token);
        return new Gson().toJson(new LogOutResponse("ok", token));
    }

    @PostMapping("/add_server")
    public String createLocalServer(@RequestParam("serverName") String serverName,
                                    HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");


        return new Gson().toJson(new Service(serverName, ServerController.startTcpServer(serverName)));
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
