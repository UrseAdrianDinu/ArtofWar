package com.chess;

import java.util.Scanner;

public class Game {
    boolean connected = false;
    int turn;
    int enginecolor;
    int usercolor;

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
            turn = enginecolor;
            Brain.getInstance().doPawnMove();
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
                Board b = Board.newGame();
                b.initBoard();
                turn = TeamColor.WHITE;
                enginecolor = TeamColor.BLACK;
                usercolor = TeamColor.WHITE;
                break;

            case "go":
                /*System.out.println("move a7a6");
                System.out.flush();*/
                break;

            case "quit":
                System.exit(0);
                break;

            case "move":
                Board.getInstance().executeMove(command);
                break;

            case "black":
                System.out.println("move a7a6");
                System.out.flush();
                enginecolor = TeamColor.BLACK;
                usercolor = TeamColor.WHITE;
                break;

            case "white":
                enginecolor = TeamColor.WHITE;
                usercolor = TeamColor.BLACK;
                break;

            case "resign":
                if (enginecolor == TeamColor.BLACK) {
                    System.out.println("Black wins");
                    System.out.flush();
                } else {
                    System.out.println("White wins");
                    System.out.flush();
                }

            default:
//                if (enginecolor == TeamColor.BLACK) {
//                    System.out.println("move a2a3");
//                    System.out.flush();
//                } else {
//                    Board.getInstance().movePawn(TeamColor.WHITE);
//                }
                break;

        }
    }

}
