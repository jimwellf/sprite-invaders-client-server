package com.example.spriteinvadersclientserver;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler
{
    private Socket clientSocket;
    private static BufferedReader in = null;
    private static PrintWriter out = null;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handleReaderPrinter() {
        in = allocateReader(clientSocket);
        out = allocateWriter(clientSocket);
        handleInput();
    }

    public void handleInput() {
        String userInput;
        try {
            while ((userInput = in.readLine()) != null) {
                System.out.println("Client: " + (userInput));
                out.println(userInput);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    public void sendJSON() {
        String jsonStr = "[ {\"name\": \"Giove\",\n" +
                " \"description\": \"desc1\"\n" +
                " },\n" +
                " {\"name\": \"Odino\",\n" +
                " \"description\": \"desc2\"\n" +
                " }\n"+
                "]\n";

        out.println(jsonStr);
    }

    private static void readJSON(String jsonStr) {

        String userInput;
        try {
            while ((userInput = in.readLine()) != null) {
                Gson gson = new Gson();
                God[] gods = gson.fromJson(jsonStr, God[].class);
                System.out.println(Arrays.toString(gods));
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    private BufferedReader allocateReader(Socket clientSocket) {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed" + e);
            return null;
        } return in;
    }

    private PrintWriter allocateWriter(Socket clientSocket) {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return out;
    }

}
