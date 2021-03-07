package com.chess;

public class Move {
    public static int CAPTURE = 0;
    public static int FREE = 1;
    public static int BLOCK = 2;

    public void executeMove(String s) {
        String[] words = s.split(" ");
        Board b = Board.getInstance();

    }
}
