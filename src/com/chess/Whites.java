package com.chess;

import java.util.ArrayList;
/*
    Clasa Whites retine informatii despre
    piesele albe

    Parametrii clasei:
        numberofpieces: numarul de piese albe
        numberofpawns: numarul de pioni albi
        whites: vector cu piesele albe

 */

public class Whites {
    int numberofpieces;
    int numberofpawns;
    ArrayList<Piece> whites;
    private static Whites instance;

    //Singleton Pattern

    private Whites() {
        whites = new ArrayList<>();
    }

    public static synchronized Whites getInstance() {
        if (instance == null)
            instance = new Whites();
        return instance;
    }

    public static synchronized void newGame() {
        instance = null;
    }

    //Metoda ce adauga o piesa alba in vector
    public void addWhitePiece(Piece piece) {
        whites.add(piece);
        numberofpieces++;
        if(piece.getType()=="Pawn")
            numberofpawns++;
    }
    //Metoda ce elimina o piesa alba din vector
    public void removeWhitePiece(Piece piece) {
        whites.remove(piece);
        numberofpieces--;
        if(piece.getType()=="Pawn")
            numberofpawns--;
    }

    //Metoda care intoarce un pion alb care
    //are mutari posibile
    public Piece getPawn() {
        Piece p = null;
        for (Piece piece : whites) {
            if (piece.getType().compareTo("Pawn") == 0) {
                piece.generateMoves();
                if ((piece.freeMoves.size() != 0 || piece.captureMoves.size() != 0)) {
                    return piece;
                }
                p = piece;
            }
        }
        return p;
    }

    public Piece getQueen() {
        for (Piece piece : whites) {
            if (piece.getType().compareTo("Queen") == 0) {
                return piece;
            }
        }
        return null;
    }

}
