package com.chess;

public class Bishop extends Piece {

    public Bishop(Coordinate coordinate, int color) {
        this.color = color;
        this.coordinate = coordinate;
    }

    @Override
    public void generateMoves() {

    }

    @Override
    public String getType() {
        return "Bishop";
    }

    @Override
    public String toString() {
        if (color == 0)
            return "WBishop ";
        return "BBishop ";
    }
}
