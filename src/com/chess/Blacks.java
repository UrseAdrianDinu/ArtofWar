package com.chess;

import java.util.ArrayList;

/*
    Clasa Blacks retine informatii despre
    piesele negre

    Parametrii clasei:
        numberofpieces: numarul de piese negre
        numberofpawns: numarul de pioni negri
        blacks: vector cu piesele negre

 */
public class Blacks {
    int numberofpieces;
    int numberofpawns;
    ArrayList<Piece> blacks;
    Piece lastMoved;
    //Singleton Pattern
    private static Blacks instance;

    private Blacks() {
        blacks = new ArrayList<>();
    }

    public static synchronized Blacks getInstance() {
        if (instance == null)
            instance = new Blacks();
        return instance;
    }

    //Metoda ce adauga o piesa neagra in vector
    public void addBlackPiece(Piece piece) {
        blacks.add(piece);
        numberofpieces++;
        if (piece.getType() == "Pawn")
            numberofpawns++;
    }

    public static synchronized void newGame() {
        instance = null;
    }

    //Metoda ce elimina o piesa neagra din vector
    public void removeBlackPiece(Piece piece) {
        blacks.remove(piece);
        numberofpieces--;
        if (piece.getType() == "Pawn")
            numberofpawns--;
    }

    //Metoda care intoarce un pion negru care
    //are mutari posibile
    public Piece getPawn() {
        Piece p = null;
        for (Piece piece : blacks) {
            if (piece.getType().compareTo("Pawn") == 0) {
                //piece.generateMoves();
                if ((piece.freeMoves.size() != 0 || piece.captureMoves.size() != 0)) {
                    return piece;
                }
                p = piece;
            }
        }
        return p;
    }

    public Piece getKnight() {
        for (Piece piece : blacks) {
            if (piece.getType().compareTo("Knight") == 0) {
                //piece.generateMoves();
                if ((piece.freeMoves.size()) != 0 || piece.captureMoves.size() != 0) {
                    return piece;
                }
            }
        }
        return null;
    }

    public Piece getPiece() {
        for (Piece piece : blacks) {
            //piece.generateMoves();
            if (piece.freeMoves.size() != 0 || piece.captureMoves.size() != 0) {
                return piece;
            }
        }
        return null;
    }

    public Coordinate getKingLocation() {
        for (Piece piece : blacks) {
            if (piece.getType().compareTo("King") == 0) {
                return piece.coordinate;
            }
        }
        return null;
    }

    public Piece getQueen() {
        for (Piece piece : blacks) {
            if (piece.getType().compareTo("Queen") == 0) {
                return piece;
            }
        }
        return null;
    }

}
