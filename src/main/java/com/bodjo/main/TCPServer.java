package com.bodjo.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public TCPServer() throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(2332);
        Socket connectionSocket = welcomeSocket.accept();
       // connectionSocket.
    }
}
