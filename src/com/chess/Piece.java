package com.chess;

import java.util.ArrayList;


public abstract class Piece {
    ArrayList<Coordinate> freeMoves;
    ArrayList<Coordinate> captureMoves;
    Coordinate coordinate;
    int color;

    public abstract void generateMoves();

    public abstract String getType();

    public abstract String toString();

    public void movePiece(Coordinate destination) {
        //change the table
        Board b = Board.getInstance();
        Piece p = b.table[9 - destination.getY()][destination.getIntX()];
        if(p!= null){
            if (p.color == TeamColor.WHITE){
                Whites.getInstance().removeWhitePiece(p);
            }
            else {
                Blacks.getInstance().removeBlackPiece(p);
            }
        }

        b.table[9 - destination.getY()][destination.getIntX()] = this;
        b.table[9 - coordinate.getY()][coordinate.getIntX()] = null;
        //change the Piece coordinate
        coordinate = destination;
    }
}