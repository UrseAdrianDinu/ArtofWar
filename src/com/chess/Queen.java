package com.chess;
/*
    Clasa specifica piesei "Regina"
 */
public class Queen extends Piece {

    //Initializare coordonata si culoare
    public Queen(Coordinate coordinate, int teamColor) {
        this.coordinate = coordinate;
        color = teamColor;
    }

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
