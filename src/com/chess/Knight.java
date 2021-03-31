package com.chess;

/*
    Clasa specifica piesei "Cal"
 */

public class Knight extends Piece {

    //Initializare coordonata si culoare
    public Knight(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public void generateMoves() {

    }

    @Override
    public String getType() {
        return "Knight";
    }


    public String toString() {
        if (color == 0)
            return "WKnight ";
        return "BKnight ";
    }
}
