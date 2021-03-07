package com.chess;

public class Queen extends Piece {


    public Queen(Coordinate coordinate, int teamColor) {
        this.coordinate = coordinate;
        color = teamColor;
    }

    @Override
    public void generateMoves() {

    }

    @Override
    public String toString() {
        if (color == 0)
            return "WQueen ";
        return "BQueen ";
    }
}
