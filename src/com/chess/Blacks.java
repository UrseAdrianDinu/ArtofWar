package com.chess;

import java.util.ArrayList;

public class Blacks {
    private int number;
    private ArrayList<Piece> blacks;
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
    }

    public void removeBlackPiece(Piece piece) {
        blacks.remove(piece);
    }

    public Piece getPawn() {
        Piece p = null;
        System.out.println("A intrat");
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
