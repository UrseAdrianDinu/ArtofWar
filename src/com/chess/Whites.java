package com.chess;

import java.util.ArrayList;
/*
    Clasa Whites retine informatii despre
    piesele albe

    Parametrii clasei:
        numberofpieces: numarul de piese albe
        numberofpawns: numarul de pioni albi
        whites: vector cu piesele albe
        lastMoved: piesa care a fost mutata ultima runda

 */

public class Whites {
    int numberofpieces;
    int numberofpawns;
    ArrayList<Piece> whites;
    Piece lastMoved;
    private static Whites instance;

    // Singleton Pattern

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

    // Metoda ce adauga o piesa alba in vector
    public void addWhitePiece(Piece piece) {
        whites.add(piece);
        numberofpieces++;
        if (piece.getType() == "Pawn")
            numberofpawns++;
    }

    // Metoda ce elimina o piesa alba din vector
    public void removeWhitePiece(Piece piece) {
        whites.remove(piece);
        numberofpieces--;
        if (piece.getType() == "Pawn")
            numberofpawns--;
    }

    // Metoda care intoarce un pion alb care
    // are mutari posibile
    public Piece getPawn() {
        Piece p = null;
        for (Piece piece : whites) {
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

    // Metoda care intoarce un cal alb care
    // are mutari posibile
    public Piece getKnight() {
        for (Piece piece : whites) {
            if (piece.getType().compareTo("Knight") == 0) {
                //piece.generateMoves();
                if ((piece.freeMoves.size()) != 0 || piece.captureMoves.size() != 0) {
                    return piece;
                }
            }
        }
        return null;
    }

    // Metoda care intoarce o regina alba care
    // are mutari posibile
    public Piece getQueen() {
        for (Piece piece : whites) {
            if (piece.getType().compareTo("Queen") == 0) {
                return piece;
            }
        }
        return null;
    }

    // Metoda care intoarce coordonata regelui
    public Coordinate getKingLocation() {
        for (Piece piece : whites) {
            if (piece.getType().compareTo("King") == 0) {
                return piece.coordinate;
            }
        }
        return null;
    }

    // Metoda care intoarce o piesa alba care
    // are mutari posibile

    public Piece getPiece() {
        for (Piece piece : whites) {
            //piece.generateMoves();
            if (!piece.getType().equals("Pawn")) {
                if (piece.freeMoves.size() != 0 || piece.captureMoves.size() != 0) {
                    return piece;
                }
            } else {
                Pawn pion = (Pawn) piece;
                if (pion.freeMoves.size() != 0 || pion.captureMoves.size() != 0 || pion.enPassantMoves.size() != 0) {
                    return piece;
                }
            }
        }
        return null;
    }

}
