package com.chess;

import java.util.ArrayList;

public class Whites {
    private int number;
    private ArrayList<Piece> whites;
    private static Whites instance;

    private Whites() {
        whites = new ArrayList<>();
    }

    public static synchronized Whites getInstance() {
        if (instance == null)
            instance = new Whites();
        return instance;
    }   //dada

    public void addWhitePiece(Piece piece) {
        whites.add(piece);
    }

    public void removeWhitePiece(Piece piece) {
        whites.remove(piece);
    }

    public Piece getPawn() {
        Piece p = null;
        System.out.println("A intrat");
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
