package com.chess;

import java.util.ArrayList;

public abstract class Piece {
    ArrayList<Coordinate> freeMoves;
    ArrayList<Coordinate> captureMoves;
    Coordinate coordinate;
    int color;

    public abstract void generateMoves();

    public abstract String toString();

    public void movePiece(Coordinate destination){
        //change the table
        Board.getInstance().table[9 - destination.getY()][destination.getIntX()] = this;
        Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()] = null;

        //change the Piece coordinate
        coordinate = destination;
    }
}