package com.chess;

import java.util.ArrayList;

public class Blacks {
    private int number;
    private ArrayList<Piece> blacks;
    private static Blacks instance;

    private Blacks(){
        blacks = new ArrayList<>();
    }

    public static synchronized Blacks getInstance() {
        if (instance == null)
            instance = new Blacks();
        return instance;
    }

    public void addBlackPiece(Piece piece){
        blacks.add(piece);
    }

    public void removeBlackPiece(Piece piece){
        blacks.remove(piece);
    }

    public Piece getPawn(){
        for(Piece piece : blacks){
            if (piece.getType().compareTo("Pawn") == 0){
                return piece;
            }
        }
        return null;
    }
}
