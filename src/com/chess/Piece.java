package com.chess;

import java.util.ArrayList;
/*
    Clasa abstracta pentru reprezentarea unei piese
    Parametrii clasei:
        freeMoves:  vector pentru mutarile libere posibile
        captureMoves: vector pentru mutarile de capturare posibile
        coordinate: coordonata piesei
        color: culoarea piesei
 */

public abstract class Piece {

    ArrayList<Coordinate> freeMoves;
    ArrayList<Coordinate> captureMoves;
    Coordinate coordinate;
    int color;
    int moves = 0;
    int support = 0;
    int turns = 0;

    public abstract void generateMoves();

    public abstract String getType();

    public abstract String toString();

    //Metoda pentru mutarea unei piese
    public void movePiece(Coordinate destination) {
        Board b = Board.getInstance();
        Piece p = b.getPiecebylocation(destination);
        //Verificam daca exita o piesa la coordonata destination
        //In functie de culoarea piesei, eliminam piesa din
        //Blacks sau Whites
        if (p != null) {
            if (p.color == TeamColor.WHITE) {
                Whites.getInstance().removeWhitePiece(p);
            } else {
                Blacks.getInstance().removeBlackPiece(p);
            }
        }
        //Updatam tabla de joc
        b.table[9 - destination.getY()][destination.getIntX()] = this;
        b.table[9 - coordinate.getY()][coordinate.getIntX()] = null;
        //Updatam coordonata piesei
        coordinate = destination;
        moves++;
    }
}