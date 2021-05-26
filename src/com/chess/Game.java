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
    int nrsaheng = 0;
    int nrsahadv = 0;
    boolean ruy_lopez = false;

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


            board.executeMove(words[0]);
//            System.out.println("BLACKS " + board.numberofblackpieces);
//            System.out.println("WHITES " + board.numberofwhitepieces);
//            System.out.println("After player's move");
//            System.out.println(board);
            changeTurn();
            gameturns++;
            Brain.getInstance().generateAllMoves(board);
            if (Brain.getInstance().checkChessEvaluation(board, enginecolor).size() > 0) {
                nrsahadv++;
            }

            // Generam toate miscarile pentru ambele culori


            // Afisarea matricelor codificate enemyattack si defense

            //System.out.println(Board.getInstance());
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
//
//            String s2 = "";
//            for (int i = 1; i <= 8; i++) {
//                for (int j = 1; j <= 8; j++) {
//                    if (Brain.getInstance().engineattack[i][j] == 0) {
//                        s2 += " 0 ";
//                    } else {
//                        s2 += " " + Brain.getInstance().engineattack[i][j] + " ";
//                    }
//                }
//                s2 += "\n";
//            }
//            System.out.println(s2);
//            System.out.println(Brain.getInstance().enemysquares);
//            System.out.println(Brain.getInstance().enginesquares);

            if (!force) {

                if (gameturns == 1) {
                    if (words[0].compareTo("e2e4") == 0) {
                        System.out.println("move e7e5");
                        board.executeMove("e7e5");
                        gameturns++;
                        changeTurn();
                    } else {
                        if (words[0].compareTo("d2d4") == 0) {
                            System.out.println("move d7d5");
                            board.executeMove("d7d5");
                            gameturns++;
                            changeTurn();
                        } else {
                            System.out.println("move e7e5");
                            board.executeMove("e7e5");
                            gameturns++;
                            changeTurn();
                        }
                    }
                } else {
                    Pair p = Brain.getInstance().alphabeta(board, null, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, nrsaheng, nrsahadv);
                    System.out.println(board.enemyChessNumber);
                    System.out.println(board.engineChessNumber);
                    System.out.println("ALPHABETA " + p);
                    turn = enginecolor;
                    System.out.println();
                    //ArrayList<Piece> chess = Brain.getInstance().checkChess(board);
                    gameturns++;
                    if (p != null) {
                        System.out.println("move " + p.c);
                        System.out.flush();
                        //Se updateaza tabla de joc
                        board.executeMove(p.c);
                        Brain.getInstance().generateAllMoves(board);
                        if (Brain.getInstance().checkChessEvaluation(board, usercolor).size() > 0) {
                            nrsaheng++;
                        }
                        changeTurn();
//                    System.out.println("After engine move");
//                    System.out.println(board);
                    } else {
                        System.out.println("resign");
                    }
                }
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
                if (gameturns == 0) {
                    System.out.println("move e2e4");
                    board.executeMove("e2e4");
                    ruy_lopez = true;
                    gameturns++;
                    force = false;
                    turn = usercolor;
                    return;
                } else {
                    Brain.getInstance().generateAllMoves(board);
                    force = false;
                    turn = enginecolor;
                    Pair p = Brain.getInstance().alphabeta(board, null, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, nrsaheng, nrsahadv);
                    System.out.println("ALPHABETA " + p);
                    turn = usercolor;
                    System.out.println();
                    gameturns++;
                    if (p != null) {
                        System.out.println("move " + p.c);
                        System.out.flush();
                        board.executeMove(p.c);
                    } else {
                        System.out.println("resign");
                    }
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
