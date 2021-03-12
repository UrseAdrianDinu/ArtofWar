package com.chess;

import java.util.ArrayList;

public class Blacks {
    int numberofpieces;
    int numberofpawns;
    ArrayList<Piece> blacks;
    private static Blacks instance;

    private Blacks() {
        blacks = new ArrayList<>();
    }

    public static synchronized Blacks getInstance() {
        if (instance == null)
            instance = new Blacks();
        return instance;
    }

    public void addBlackPiece(Piece piece) {
        blacks.add(piece);
        numberofpieces++;
        if(piece.getType()=="Pawn")
            numberofpawns++;
    }

    public static synchronized void newGame() {
        instance = null;
    }

    public void removeBlackPiece(Piece piece) {
        blacks.remove(piece);
        numberofpieces--;
        if(piece.getType()=="Pawn")
            numberofpawns--;
    }

    public Piece getPawn() {
        Piece p = null;
        for (Piece piece : blacks) {
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

}
