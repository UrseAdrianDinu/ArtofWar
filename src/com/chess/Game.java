package com.chess;

import java.util.Scanner;

/*
    Clasa Game citeste de la standard input comenzi, pe
    care le va interpreta si va updata tabla de joc
    si starea jocului.
    Parametrii clasei:
        -connected: flag care arata daca jocul s-a conectat la interfata xboard
        -turn: arata cine urmeaza sa faca o miscare
        -enginecolor/usercolor: parametrii pentru culorile echipelor
        -force: indicator pentru force mode
 */
public class Game {

    boolean connected = false;
    int turn;
    int enginecolor;
    int usercolor;
    boolean force;

    //Singleton Pattern
    private static Game instance = null;

    private Game() {

    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    //Metoda care citeste comenzi si le proceseaza
    public void readInput() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            if (command == null) return;
            processCommand(command);
        }
    }

    //Metoda care proceseaza o comanda
    public void processCommand(String command) {
        String[] words = command.split(" ");
        //Verifica daca a primit o mutare de la xboard
        if (words[0].length() > 3 && Character.isDigit(words[0].charAt(1)) &&
                Character.isDigit(words[0].charAt(3))) {

            if (words[0].compareTo("e8g8") == 0) {
                Board.getInstance().executeMove("h8f8");
            }
            if (words[0].compareTo("e1g1") == 0) {
                Board.getInstance().executeMove("h1f1");
            }
            if (words[0].compareTo("e8c8") == 0) {
                Board.getInstance().executeMove("a8d8");
            }
            if (words[0].compareTo("e1c1") == 0) {
                Board.getInstance().executeMove("a1d1");
            }
            Board.getInstance().executeMove(words[0]);
            Brain.getInstance().generateAllMoves();
            System.out.println(Board.getInstance());
            String s = "";
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    if (Brain.getInstance().enemyattack[i][j] == 0) {
                        s += " 0 ";
                    } else {
                        s += " " + Brain.getInstance().enemyattack[i][j] + " ";
                    }
                }
                s += "\n";
            }
            System.out.println(s);
            String s1 = "";
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    if (Brain.getInstance().defense[i][j] == 0) {
                        s1 += " 0 ";
                    } else {
                        s1 += " " + Brain.getInstance().defense[i][j] + " ";
                    }
                }
                s1 += "\n";
            }
            System.out.println(s1);
            if (!force) {
                turn = enginecolor;
                Piece chess = Brain.getInstance().checkChess();
                System.out.println(Board.getInstance());
                if (chess != null) {
                    System.out.println("SAH");
                    boolean protecc = Brain.getInstance().protectKing(chess);
                    if (protecc == false) {
                        System.out.println("resign");
                    }
                } else {
                    String rocada = Brain.getInstance().checkCastlingconditions();
                    System.out.println(rocada);
                    if (rocada.compareTo("") != 0) {
                        String[] castlings = rocada.split(" ");
                        System.out.println("DADADA" + castlings[0]);
                        if (castlings[0].compareTo("mica") == 0) {
                            if (Game.getInstance().enginecolor == TeamColor.BLACK) {
                                System.out.println("move e8g8");
                                Board.getInstance().executeMove("e8g8");
                                Board.getInstance().executeMove("h8f8");
                                return;
                            } else {
                                System.out.println("move e1g1");
                                Board.getInstance().executeMove("e1g1");
                                Board.getInstance().executeMove("h1f1");
                                return;
                            }
                        } else {
                            if (Game.getInstance().enginecolor == TeamColor.BLACK) {
                                System.out.println("move e8c8");
                                Board.getInstance().executeMove("e8c8");
                                Board.getInstance().executeMove("a8d8");
                                return;
                            } else {
                                System.out.println("move e1c1");
                                Board.getInstance().executeMove("e1c1");
                                Board.getInstance().executeMove("a1d1");
                                return;
                            }
                        }
                    }
                    System.out.println("DOMOVE");
                    Brain.getInstance().doMove();
                    System.out.println(Board.getInstance());
                }
                turn = usercolor;
            }
        }

        //Am procesat comenzile din cerinta
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
                break;

            case "go":
                Brain.getInstance().generateAllMoves();
                force = false;
                turn = enginecolor;
                Brain.getInstance().doMove();
                turn = usercolor;
                break;

            case "quit":
                System.exit(0);
                break;

            case "move":
                Board.getInstance().executeMove(words[0]);
                break;

            case "black":

                enginecolor = TeamColor.BLACK;
                usercolor = TeamColor.WHITE;
                break;

            case "force":
                force = true;

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
        }
    }
}
