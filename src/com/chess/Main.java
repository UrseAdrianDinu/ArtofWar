package com.chess;

public class Main {

    public static void main(String[] args) {
        //XboardConnection.getInstance().readInput();
        Board b=Board.getInstance();
        b.initBoard();
        System.out.println(b);
    }
}
