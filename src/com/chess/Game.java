package com.chess;

import java.util.ArrayList;
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
    int turn = TeamColor.WHITE;
    int enginecolor;
    int usercolor;
    boolean force;
    int gameturns;
    Board board;

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
    public void readInput() throws CloneNotSupportedException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            if (command == null) return;
            processCommand(command);
        }
    }

    void changeTurn() {
        if (turn == TeamColor.WHITE) {
            turn = TeamColor.BLACK;
        } else {
            turn = TeamColor.WHITE;
        }
    }

    //Metoda care proceseaza o comanda
    public void processCommand(String command) throws CloneNotSupportedException {
        String[] words = command.split(" ");
        //Verifica daca a primit o mutare de la xboard
        if (words[0].length() > 3 && Character.isDigit(words[0].charAt(1)) &&
                Character.isDigit(words[0].charAt(3))) {

            // Verificam daca utilizatorul a facaut o rocada
            if (words[0].compareTo("e8g8") == 0) {
                board.executeMove("h8f8");
            }
            if (words[0].compareTo("e1g1") == 0) {
                board.executeMove("h1f1");
            }
            if (words[0].compareTo("e8c8") == 0) {
                board.executeMove("a8d8");
            }
            if (words[0].compareTo("e1c1") == 0) {
                board.executeMove("a1d1");
            }

            board.executeMove(words[0]);
            System.out.println("BLACKS " + board.numberofblackpieces);
            System.out.println("WHITES " + board.numberofwhitepieces);
            System.out.println("EXECUTE");
            System.out.println(board);
            changeTurn();
            gameturns++;

            // Generam toate miscarile pentru ambele culori


            // Afisarea matricelor codificate enemyattack si defense

//            System.out.println(Board.getInstance());
//            String s = "";
//            for (int i = 1; i <= 8; i++) {
//                for (int j = 1; j <= 8; j++) {
//                    if (Brain.getInstance().enemyattack[i][j] == 0) {
//                        s += " 0 ";
//                    } else {
//                        s += " " + Brain.getInstance().enemyattack[i][j] + " ";
//                    }
//                }
//                s += "\n";
//            }
//            System.out.println(s);
//            String s1 = "";
//            for (int i = 1; i <= 8; i++) {
//                for (int j = 1; j <= 8; j++) {
//                    if (Brain.getInstance().defense[i][j] == 0) {
//                        s1 += " 0 ";
//                    } else {
//                        s1 += " " + Brain.getInstance().defense[i][j] + " ";
//                    }
//                }
//                s1 += "\n";
//            }
//            System.out.println(s1);

            if (!force) {
                Brain.getInstance().generateAllMoves(board);
               Pair p = Brain.getInstance().alphabeta(board,null,8,Integer.MIN_VALUE,Integer.MAX_VALUE,true);
                System.out.println("ALPHABETA " + p);
                turn = enginecolor;
                ArrayList<Piece> chess = Brain.getInstance().checkChess(board);
                gameturns++;
                // Verificam daca am primit sah
                if (chess.size() > 0) {
                    // In acast caz apelam protectKing pentru a scoate regele din sah
                    boolean protecc = Brain.getInstance().protectKing(chess, board);
                    if (!protecc) {
                        System.out.println("resign");
                    }
                } else {
                    // Am verificat daca putem sa facem rocada
//                    String rocada = Brain.getInstance().checkCastlingconditions(board);
//                    if (rocada.compareTo("") != 0) {
//                        // In functie de culoarea engine-ului se executa rocada mica/mare
//                        String[] castlings = rocada.split(" ");
//                        if (castlings[0].compareTo("mica") == 0) {
//                            if (Game.getInstance().enginecolor == TeamColor.BLACK) {
//                                System.out.println("move e8g8");
//                                board.executeMove("e8g8");
//                                board.executeMove("h8f8");
//                                return;
//                            } else {
//                                System.out.println("move e1g1");
//                                board.executeMove("e1g1");
//                                board.executeMove("h1f1");
//                                return;
//                            }
//                        } else {
//                            if (Game.getInstance().enginecolor == TeamColor.BLACK) {
//                                System.out.println("move e8c8");
//                                board.executeMove("e8c8");
//                                board.executeMove("a8d8");
//                                return;
//                            } else {
//                                System.out.println("move e1c1");
//                                board.executeMove("e1c1");
//                                board.executeMove("a1d1");
//                                return;
//                            }
//                        }
//                    }

                    if (enginecolor == TeamColor.BLACK) {
                        System.out.println(board.getBlackKing().freeMoves);
                        if (board.getBlackKing().freeMoves.contains(board.getCoordinates(7, 8))) {
                            System.out.println("move e8g8");
                            board.executeMove("e8g8");
                            board.executeMove("h8f8");
                            return;
                        }
                        if (board.getBlackKing().freeMoves.contains(board.getCoordinates(3, 8))) {
                            System.out.println("move e8c8");
                            board.executeMove("e8c8");
                            board.executeMove("a8d8");
                            return;
                        }
                    } else {
                        if (board.getWhiteKing().freeMoves.contains(board.getCoordinates(7, 1))) {
                            System.out.println("move e1g1");
                            board.executeMove("e1g1");
                            board.executeMove("h1f8");
                            return;
                        }
                        if (board.getBlackKing().freeMoves.contains(board.getCoordinates(3, 1))) {
                            System.out.println("move e1c1");
                            board.executeMove("e1c1");
                            board.executeMove("a1d1");
                            return;
                        }
                    }

                    Brain.getInstance().doMove(board);

                }
                changeTurn();
                System.out.println("After engine move");
                System.out.println(board);
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
                board = new Board();

                board.numberofblackpieces = 0;
                board.numberofblackpawns = 0;
                board.numberofwhitepieces = 0;
                board.numberofwhitepawns = 0;

                board.initBoard();
                turn = TeamColor.WHITE;
                enginecolor = TeamColor.BLACK;
                Game.getInstance().gameturns = 0;

                usercolor = TeamColor.WHITE;
                force = false;
                break;

            case "go":
                Brain.getInstance().generateAllMoves(board);
                force = false;
                turn = enginecolor;
                ArrayList<Piece> chess = Brain.getInstance().checkChess(board);
                System.out.println(board);
                gameturns++;
                // Verificam daca am primit sah
                if (chess.size() > 0) {
                    // In acast caz apelam protectKing pentru a scoate regele din sah
                    boolean protecc = Brain.getInstance().protectKing(chess, board);
                    if (!protecc) {
                        System.out.println("resign");
                    }
                } else {
                    // Am verificat daca putem sa facem rocada
                    String rocada = Brain.getInstance().checkCastlingconditions(board);
                    System.out.println(rocada);
                    if (rocada.compareTo("") != 0) {
                        // In functie de culoarea engine-ului se executa rocada mica/mare
                        String[] castlings = rocada.split(" ");
                        System.out.println("DADADA" + castlings[0]);
                        if (castlings[0].compareTo("mica") == 0) {
                            if (Game.getInstance().enginecolor == TeamColor.BLACK) {
                                System.out.println("move e8g8");
                                board.executeMove("e8g8");
                                board.executeMove("h8f8");
                                return;
                            } else {
                                System.out.println("move e1g1");
                                board.executeMove("e1g1");
                                board.executeMove("h1f1");
                                return;
                            }
                        } else {
                            if (Game.getInstance().enginecolor == TeamColor.BLACK) {
                                System.out.println("move e8c8");
                                board.executeMove("e8c8");
                                board.executeMove("a8d8");
                                return;
                            } else {
                                System.out.println("move e1c1");
                                board.executeMove("e1c1");
                                board.executeMove("a1d1");
                                return;
                            }
                        }
                    }
                    Brain.getInstance().doMove(board);
                    System.out.println(board);
                }
                changeTurn();
                break;

            case "quit":
                System.exit(0);
                break;

            case "move":
                board.executeMove(words[0]);
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
