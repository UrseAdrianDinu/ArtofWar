package com.chess;

public class Knight extends Piece {

    public Knight(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public void generateMoves() {

    }

    public String toString() {
        if (color == 0)
            return "WKnight ";
        return "BKnight ";
    }
}
