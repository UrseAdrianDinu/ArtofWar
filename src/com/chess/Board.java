package com.chess;

import java.util.Arrays;
import java.util.HashMap;

public class Board {
    private static Board instance;
    Coordinate[][] coordinates;
    Piece[][] table;
    HashMap<Character, Integer> dictionar;

    private Board() {
        table = new Piece[9][9];
    }

    void initdictionar() {
        dictionar.put('a', 1);
        dictionar.put('b', 2);
        dictionar.put('c', 3);
        dictionar.put('d', 4);
        dictionar.put('e', 5);
        dictionar.put('f', 6);
        dictionar.put('g', 7);
        dictionar.put('h', 8);
    }

    public static synchronized Board getInstance() {
        if (instance == null)
            instance = new Board();
        return instance;
    }

    public static synchronized Board newGame() {
        instance = null;
        return getInstance();
    }

    public Coordinate getCoordinates(int x, int y) {
        if (coordinates == null) {
            coordinates = new Coordinate[9][9];
        }
        if (coordinates[x][y] != null) {
            return coordinates[x][y];
        }
        coordinates[x][y] = new Coordinate(x, y);

        return coordinates[x][y];
    }

    public int isEmpty(Coordinate coordinate, int color) {
        if (table[coordinate.getIntX()][coordinate.getY()] == null) {
            return Move.FREE;
        }
        if (table[coordinate.getIntX()][coordinate.getY()].color == color) {
            return Move.BLOCK;
        }
        return Move.CAPTURE;
    }

    void initBoard() {
        int i;
        for (i = 1; i <= 8; i++) {
            table[2][i] = new Pawn(new Coordinate(i, 7), TeamColor.BLACK);
            table[7][i] = new Pawn(new Coordinate(i, 2), TeamColor.WHITE);
        }

        table[1][5] = new King(new Coordinate(5, 8), TeamColor.BLACK);
        table[8][5] = new King(new Coordinate(5, 1), TeamColor.WHITE);

        table[1][4] = new Queen(new Coordinate(4, 8), TeamColor.BLACK);
        table[8][4] = new Queen(new Coordinate(4, 1), TeamColor.WHITE);

        table[1][1] = new Rook(new Coordinate(1, 8), TeamColor.BLACK);
        table[1][8] = new Rook(new Coordinate(8, 8), TeamColor.BLACK);
        table[8][1] = new Rook(new Coordinate(1, 1), TeamColor.WHITE);
        table[8][8] = new Rook(new Coordinate(8, 1), TeamColor.WHITE);

        table[1][3] = new Bishop(new Coordinate(3, 8), TeamColor.BLACK);
        table[1][6] = new Bishop(new Coordinate(6, 8), TeamColor.BLACK);
        table[8][3] = new Bishop(new Coordinate(3, 1), TeamColor.WHITE);
        table[8][6] = new Bishop(new Coordinate(6, 1), TeamColor.WHITE);

        table[1][2] = new Knight(new Coordinate(2, 8), TeamColor.BLACK);
        table[1][7] = new Knight(new Coordinate(7, 8), TeamColor.BLACK);
        table[8][2] = new Knight(new Coordinate(2, 1), TeamColor.WHITE);
        table[8][7] = new Knight(new Coordinate(7, 1), TeamColor.WHITE);

    }

    public Piece getPiecebylocation(Coordinate coordinate) {
        return table[9 - coordinate.getY()][coordinate.getIntX()];
    }

    public void executeMove(String s) {
        String[] words = s.split(" ");
        Board b = Board.getInstance();
        //System.out.println(words[1]);
        char xi = words[1].charAt(0);
        int yi = words[1].charAt(1) - 48;
        char xf = words[1].charAt(2);
        int yf = words[1].charAt(3) - 48;
        Piece p = b.getPiecebylocation(new Coordinate(xi, yi));
        //System.out.println(p + " coord " + p.coordinate);
        b.table[9 - yf][xf - 96] = p;
        b.table[9 - yi][xi - 96] = null;
        p.coordinate.setX(xf - 96);
        p.coordinate.setY(yf);
        //System.out.println(p + " coord " + p.coordinate);
        //System.out.println(xi + " " + yi + " -> " + xf + " " + yf);
    }

    @Override
    public String toString() {
        String s = "";
        int i, j;
        for (i = 1; i <= 8; i++) {
            for (j = 1; j <= 8; j++) {
                if (table[i][j] == null)
                    s += " null ";
                else
                    s += table[i][j];
            }
            s += "\n";
        }
        return s;
    }
}
