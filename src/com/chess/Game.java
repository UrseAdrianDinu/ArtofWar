package com.chess;

import java.util.Scanner;

public class Game {
    boolean connected = false;
    int turn;
    int enginecolor;
    int usercolor;
    boolean force;

    private static Game instance = null;

    private Game() {

    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;

    }

    public void readInput() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            //quitSystem.out.println(command);
            if (command == null) return;
            processCommand(command);
        }
    }

    public void processCommand(String command) {
        String[] words = command.split(" ");

        if (words[0].length() > 3 && Character.isDigit(words[0].charAt(1)) &&
                Character.isDigit(words[0].charAt(3))) {

            Board.getInstance().executeMove(words[0]);
            if (!force) {
                turn = enginecolor;
                Brain.getInstance().setPiece();
                if (Brain.getInstance().piece != null)
                    Brain.getInstance().doPawnMove();
                else {
                    System.out.println("resign");
                    System.out.flush();
                }

                turn = usercolor;
            }
        }

        switch (words[0]) {
            case "xboard":
                connected = true;
                break;
            case "protover":
                System.out.println("feature myname=\"Art of War\" sigterm=0 sigint=0 san=0");
                System.out.flush();
                break;

            case "new":
                Blacks.getInstance().blacks = null;
                Whites.getInstance().whites = null;
                Blacks.newGame();
                Whites.newGame();
                Blacks.getInstance().numberofpieces = 0;
                Blacks.getInstance().numberofpawns = 0;
                Whites.getInstance().numberofpieces = 0;
                Whites.getInstance().numberofpawns = 0;
                Board b = Board.newGame();
                b.initBoard();
                turn = TeamColor.WHITE;
                enginecolor = TeamColor.BLACK;
                usercolor = TeamColor.WHITE;
                force = false;
                System.out.println(b);
                break;

            case "go":
                /*System.out.println("move a7a6");
                System.out.flush();*/
                System.out.println(enginecolor + "go ----------" + usercolor);
                turn = enginecolor;
                Brain.getInstance().doPawnMove();
                turn = usercolor;
                force = false;
                break;

            case "quit":
                System.exit(0);
                break;

            case "move":
                Board.getInstance().executeMove(command);
                break;

            case "black":

                Brain.getInstance().setColor(TeamColor.BLACK);
                Brain.getInstance().setPiece();
                enginecolor = TeamColor.BLACK;
                usercolor = TeamColor.WHITE;
                System.out.println(enginecolor + "----------" + usercolor);
                break;

            case "force":
                force = true;
                break;

            case "white":
                Brain.getInstance().setColor(TeamColor.WHITE);
                Brain.getInstance().setPiece();
                enginecolor = TeamColor.WHITE;
                usercolor = TeamColor.BLACK;
                System.out.println(enginecolor + "----------" + usercolor);
                break;

            case "resign":
                if (enginecolor == TeamColor.BLACK) {
                    System.out.println("Black wins");
                    System.out.flush();
                } else {
                    System.out.println("White wins");
                    System.out.flush();
                }
        }
    }

}
