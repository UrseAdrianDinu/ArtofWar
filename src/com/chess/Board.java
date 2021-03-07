package com.chess;

import java.util.Arrays;

public class Board {
    private static Board instance;
    Coordinate[][] coordinates;
    Piece[][] table;

    private Board() {
        table = new Piece[9][9];
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
        ;
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
            table[2][i] = new Pawn(i, 2, TeamColor.WHITE);
            table[7][i] = new Pawn(i, 7, TeamColor.BLACK);
        }
    }

    public Piece getPiecebylocation(Coordinate coordinate) {
        return table[coordinate.getIntX()][coordinate.getY()];
    }

    @Override
    public String toString() {
        String s = "";
        int i, j;
        for (i = 1; i <= 8; i++) {
            for (j = 1; j <= 8; j++) {
                if (table[i][j] == null)
                    s += "null ";
                else
                    s +=  table[i][j];
            }
            s += "\n";
        }
        return s;
    }
}
