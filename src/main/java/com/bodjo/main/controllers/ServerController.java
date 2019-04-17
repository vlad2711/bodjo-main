package com.bodjo.main.controllers;

import com.bodjo.main.MainApplication;
import com.bodjo.main.Utils.Utils;
import com.bodjo.main.objects.SSL;
import com.bodjo.main.objects.ServerStartupModel;
import com.bodjo.main.objects.Table;
import com.bodjo.main.objects.UserModel;
import com.google.gson.Gson;
import org.springframework.util.SocketUtils;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Scanner;

public class ServerController {
    public static HashMap<String, Process> servers = new HashMap<>();
    public static HashMap<String, Integer> ports = new HashMap<>();

    public static void runServer(String path, String gameName) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("sh", path + gameName + "/start.sh");

        builder.directory(new File(path + gameName));

        Process process = builder.start();
        int port = SocketUtils.findAvailableTcpPort();

        servers.put(gameName, process);
        ports.put(gameName, port);

        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getErrorStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));


        System.out.println(port);

        String s = new Gson().toJson(new ServerStartupModel("instruction", new Table(gameName, Utils.dbPassword), port, new SSL("../../ssl/cert.pem", "../../ssl/privkey.pem"))) + "\n";

        System.out.println(s);
        writer.write(s);
        writer.flush();

        System.out.println("wait");
        int exitCode = process.waitFor();
        System.out.println("I am dead " + gameName);

        Scanner scanner = new Scanner(stdout);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        System.out.println(exitCode);

        servers.remove(gameName);
        ports.remove(gameName);
    }

    public static void addUser(String gameName, String playerName, String token) throws IOException {
        if(servers.containsKey(gameName)) {
            Process process = servers.get(gameName);

            OutputStream stdin = process.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

            String s = new Gson().toJson(new UserModel("newPlayer", playerName, token)) + "\n";

            System.out.println(s);
            writer.write(s);
            writer.flush();
        }
    }
}
