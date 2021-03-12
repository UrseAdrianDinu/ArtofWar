package com.chess;

import java.util.ArrayList;

public class Whites {
    int numberofpieces;
    int numberofpawns;

    ArrayList<Piece> whites;
    private static Whites instance;

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

    public void addWhitePiece(Piece piece) {
        whites.add(piece);
        numberofpieces++;
        if(piece.getType()=="Pawn")
            numberofpawns++;
    }

    public void removeWhitePiece(Piece piece) {
        whites.remove(piece);
        numberofpieces--;
        if(piece.getType()=="Pawn")
            numberofpawns--;
    }

    public Piece getPawn() {
        Piece p = null;
        for (Piece piece : whites) {
            if (piece.getType().compareTo("Pawn") == 0) {
                piece.generateMoves();
                if ((piece.freeMoves.size() != 0 || piece.captureMoves.size() != 0)) {
                    System.out.println("" + piece.freeMoves + " " + piece.captureMoves);
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
