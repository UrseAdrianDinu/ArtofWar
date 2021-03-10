package com.chess;

import java.util.ArrayList;

public class Whites {
    private int number;
    private ArrayList<Piece> whites;
    private static Whites instance;

    private Whites(){
        whites = new ArrayList<>();
    }

    public static synchronized Whites getInstance() {
        if (instance == null)
            instance = new Whites();
        return instance;
    }

    public void addWhitePiece(Piece piece){
        whites.add(piece);
    }

    public Piece getPawn(){
        for(Piece piece : whites){
            if (piece.getType().compareTo("Pawn") == 0){
                return piece;
            }
        }
        return null;
    }
}
