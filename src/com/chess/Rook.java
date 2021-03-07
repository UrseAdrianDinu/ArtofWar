package com.chess;

public class Rook extends Piece {

    public Rook(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public void generateMoves() {

    }

    public String toString() {
        if (color == 0)
            return "WRook ";
        return "BRook ";
    }
}
