package com.example.spriteinvadersclientserver;

import javafx.application.Platform;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMain {

    private static int portNumber = 1234;
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;

    private static Timer timer;

    public static void main(String[] args)
    {
        timer = startTimer();

        serverSocket = openServer();
        if (serverSocket == null) {
            return;
        }

        System.out.println("Server started!");

        while (true) {
            clientSocket = openClientSocket();
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            Thread th = new Thread(() ->
            {
                clientHandler.handleReaderPrinter();
                clientHandler.sendJSON();
                closeClientSocket();
            });

            th.start();
        }
    }

    private static Timer startTimer() {
        Timer timer = new Timer("Timer");

        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                });
            }
        };

        return timer;
    }

    private static ServerSocket openServer() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return serverSocket;
    }

    private static Socket openClientSocket() {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Server accepted");
        } catch (IOException e) {
            System.out.println("Accept failed" + e);
            return null;
        } return clientSocket;
    }

    private static void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}