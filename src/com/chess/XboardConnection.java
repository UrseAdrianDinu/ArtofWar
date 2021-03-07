package com.chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class XboardConnection {
    private static XboardConnection instance = null;
    boolean connected = false;

    private XboardConnection() {

    }

    public static XboardConnection getInstance() {
        if (instance == null)
            instance = new XboardConnection();
        return instance;
    }

    public void readInput() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String command = input.readLine();
                if (command == null) return;
                processCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void processCommand(String command) {
        String[] words = command.split(" ");

        switch (words[0]) {
            case "xboard":
                connected = true;
                System.out.println();
                break;
            case "protover":
                System.out.println("feature myname=\"Art of War\" sigterm=0 sigint=0 san=0");
                System.out.flush();
                break;
            case "new":
                Board b = Board.newGame();
                b.initBoard();
                break;
            case "go":
                System.out.println("move e7e6");
                System.out.flush();
                break;
            case "quit":
                System.exit(0);
                break;
            case "move":
                System.out.println("move e7e6");
                System.out.flush();
                break;
            default:
                System.out.println("error");
                System.out.flush();
                break;

        }
    }
}