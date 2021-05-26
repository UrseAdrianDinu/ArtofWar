package com.chess;

import java.util.ArrayList;
/*
    Clasa abstracta pentru reprezentarea unei piese
    Parametrii clasei:
        freeMoves:  vector pentru mutarile libere posibile
        captureMoves: vector pentru mutarile de capturare posibile
        coordinate: coordonata piesei
        color: culoarea piesei
        moves: numarul de miscari realizate de piesa curenta
        support: numarul de piese care apara piesa curenta
        turns: numarul rundei
 */

public abstract class Piece implements Cloneable {

    ArrayList<Coordinate> freeMoves;
    ArrayList<Coordinate> captureMoves;
    Coordinate coordinate;
    int color;
    int value;
    int moves = 0;
    int support = 0;
    int turns = 0;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public abstract void generateMoves(Board booard);

    public abstract String getType();

    public abstract int getTypeint();

    public abstract String toString();

    // Metoda pentru mutarea unei piese
    public void movePiece(Coordinate destination, Board b) {
        Piece p = b.getPiecebylocation(destination);
        // Verificam daca exista o piesa la coordonata destination
        // In functie de culoarea piesei, eliminam piesa din
        // Blacks sau Whites
        if (p != null) {
            if (p.color == TeamColor.WHITE) {
                b.whites.remove(p);
            } else {
                b.blacks.remove(p);
            }
        }
        // Updatam tabla de joc
        b.table[9 - destination.getY()][destination.getIntX()] = this;
        b.table[9 - coordinate.getY()][coordinate.getIntX()] = null;
        // Updatam coordonata piesei
        coordinate = destination;
        moves++;
    }
}