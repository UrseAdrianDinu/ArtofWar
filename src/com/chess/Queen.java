package com.chess;

public class Queen extends Piece {

    public Queen(Coordinate coordinate, int teamColor) {
        this.coordinate = coordinate;
        color = teamColor;
    }
    //dada
    @Override
    public void generateMoves() {

    }

    @Override
    public String getType() {
        return "Queen";
    }

    @Override
    public String toString() {
        if (color == 0)
            return "WQueen ";
        return "BQueen ";
    }
}
