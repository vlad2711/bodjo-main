package com.bodjo.main;

import com.bodjo.main.Utils.Utils;
import com.bodjo.main.objects.SSL;
import com.bodjo.main.objects.ServerStartupModel;
import com.bodjo.main.objects.Table;
import com.bodjo.main.objects.UserModel;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {
    public int port;

    private Socket socket;
    private ServerSocket server;
    private OutputStream out =  null;

    public void run(int port) {
        try {
            this.port = port;
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = null;
        while (true) {
            if (socket != null && socket.isConnected()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    socket = server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addPlayer(String playerName, String token) throws IOException {
        if (socket == null || !socket.isConnected()) {
            return;
        }

        out = socket.getOutputStream();

        final PrintStream printStream = new PrintStream(out);
        String s = new Gson().toJson(new UserModel("newPlayer", playerName, token));
        printStream.print(s);
        printStream.flush();

    }
}
