package com.example.spriteinvadersclientserver;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;


public class ClientMain
{
    private static String hostName = "127.0.0.1";
    private static int portNumber = 1234;

    private static Socket echoSocket = null;

    private static PrintWriter out = null;
    private static BufferedReader in = null;

    public static void main(String[] args)
    {
        connectToServer();

        allocatePrinter();
        allocateWriter();

        serverInput();
    }


    // ————————————————— START: Client functions.

    private static void connectToServer() {
        try {
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("Cannot reach server " + e);
        }
        System.out.println("Write something");
    }

    private static void allocatePrinter() {
        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Writer failed. " + e);
        }
    }

    private static void allocateWriter() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed. " + e);
        }
    }


    private static void serverInput() {
        BufferedReader stdIn;
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        while (true) {
            try {
                if ((userInput = stdIn.readLine()) != null)
                    out.println(userInput);
                System.out.println("Server: " + in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ————————————————— END: Client functions.
}
