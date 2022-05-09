package com.example.spriteinvadersclientserver;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMain {

    static int portNumber = 1234;
    static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;

    public static void main(String[] args)
    {
        String hostName = "127.0.0.1";
        int portNumber = 1234;

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
                closeClientSocket();
            });

            th.start();
        }
    }

    private void startTimer(Stage primaryStage) {
        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                });
            }
        };
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