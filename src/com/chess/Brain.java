package com.chess;

import java.util.ArrayList;
import java.util.Random;

/*
    Clasa care implementeaza gandirea engine-ului.
    Pentru moment, ea decide mutarile pionilor.
 */
public class Brain {

    //Singleton Pattern
    private static Brain instance = null;
    int[][] enemyattack;
    int[][] defense;
    ArrayList<Piece> invalidPieces = new ArrayList<>();
    ArrayList<Integer> freeDirection = new ArrayList<>();
    Piece blackKing;
    Piece whiteKing;

    private Brain() {

    }

    public static Brain getInstance() {
        if (instance == null)
            instance = new Brain();
        return instance;
    }

    void generateAllMoves() {
        defense = new int[9][9];
        enemyattack = new int[9][9];


        int i = 0;
        if ( invalidPieces.size() != 0 ) {
            System.out.println("MIMI" + invalidPieces + invalidPieces.get(0).coordinate);
        }
        System.out.println();

        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            for (Piece p : Whites.getInstance().whites) {
               // if (!invalidPieces.contains(p)) {
                    p.generateMoves();
               //     System.out.println("OOOO " + p.coordinate);
               // } else {
               //     System.out.println("DINU " + p);
               // }
                addPiece(p);
                if (p.getType().compareTo("Pawn") != 0) {
                    for (Coordinate c : p.freeMoves) {
                        enemyattack[9 - c.getY()][c.getIntX()] = 4;
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() + 1 <= 8) {
                        int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] = 4;
                    }
                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() + 1 <= 8) {
                        int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] = 4;
                    }
                }
            }

            for (Piece p : Blacks.getInstance().blacks) {
               // if (!invalidPieces.contains(p)) {
                    p.generateMoves();
               //     System.out.println("OOOO " + p.coordinate);
              //  } else {
              //      System.out.println("DINU " + p + " - " + p.coordinate + "-" + p.freeMoves + " " + p.captureMoves);
              //  }
                addPiece(p);
                for (Coordinate c : p.freeMoves) {
                    defense[9 - c.getY()][c.getIntX()] = 4;
                }

            }
        } else {
            for (Piece p : Blacks.getInstance().blacks) {
             //   if (!invalidPieces.contains(p)) {
                    p.generateMoves();
             //       System.out.println("OOOO " + p.coordinate);
            //    } else {
            //        System.out.println("DINU " + p);
            //    }
                addPiece(p);
                if (p.getType().compareTo("Pawn") != 0) {
                    for (Coordinate c : p.freeMoves) {
                        enemyattack[9 - c.getY()][c.getIntX()] = 4;
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() - 1 >= 1) {
                        int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] = 4;
                    }
                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() - 1 >= 1) {
                        int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] = 4;
                    }
                }
            }

            for (Piece p : Whites.getInstance().whites) {
             //   if (!invalidPieces.contains(p)) {
                    p.generateMoves();
             //       System.out.println("OOOO " + p.coordinate);
             //   } else {
             //       System.out.println("DINU " + p);
             //   }
                addPiece(p);
                for (Coordinate c : p.freeMoves) {
                    defense[9 - c.getY()][c.getIntX()] = 4;
                }

            }
        }

        verifyInvalidMoves();
        Piece KingInChess = checkChess();

    }

    public void verifyInvalidMoves() {
        Piece OurKing;
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            OurKing = blackKing;
        } else {
            OurKing = whiteKing;
        }

        invalidPieces = new ArrayList<>();
        freeDirection = new ArrayList<>();
        Coordinate KingCoordinate = OurKing.coordinate;
        Piece pieceFromTable;
        Coordinate pieceFromTableCoordinate;
        ArrayList<Piece> queuePiece = new ArrayList<>();

        //LINIE - SUS
        int X = KingCoordinate.getIntX();
        int Y = KingCoordinate.getY();
        Y++;
        int FLAG = 0;
        while (Y <= 8) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Rook") == 0) {

                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).freeMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).captureMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                        //queuePiece.get(0).captureMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove){
                                    pawn.enPassantMoves.remove(c);
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            Y++;
        }

        //LINIE - JOS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        Y--;
        queuePiece = new ArrayList<>();
        FLAG = 0;
        while (Y >= 1) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Rook") == 0) {

                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).freeMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).captureMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                        //queuePiece.get(0).captureMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove){
                                    pawn.enPassantMoves.remove(c);
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            Y--;
        }

        //LINIE - DREAPTA
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X++;
        while (X <= 8) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Rook") == 0) {

                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    //queuePiece.get(0).freeMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    //queuePiece.get(0).captureMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                        //queuePiece.get(0).captureMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove){
                                    pawn.enPassantMoves.remove(c);
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            X++;
        }

        //LINIE - STANGA
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X--;
        while (X >= 1) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Rook") == 0) {

                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    //queuePiece.get(0).freeMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    //queuePiece.get(0).captureMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                        //queuePiece.get(0).captureMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove){
                                    pawn.enPassantMoves.remove(c);
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            X--;
        }

        //DIAGONALA = STANGA-JOS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X--;
        Y--;
        while (X >= 1 && Y >= 1) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Bishop") == 0) {

                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).freeMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).captureMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                                queuePiece.get(0).coordinate.getY()) {
                                            //queuePiece.get(0).captureMoves.remove(c);
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove){
                                        pawn.enPassantMoves.remove(c);
                                    }
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            X--;
            Y--;
        }

        //DIAGONALA = DREAPTA-JOS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X++;
        Y--;
        while (X <= 8 && Y >= 1) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Bishop") == 0) {
                            System.out.println("HAHA " + queuePiece.get(0) + "-" + queuePiece.get(0).coordinate);
                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            if (queuePiece.get(0).freeMoves != null) {
                                for (Coordinate c : queuePiece.get(0).freeMoves) {
                                    System.out.println("MATEI" + queuePiece.get(0).freeMoves.size());
                                    if ((c.getIntX() + c.getY()) != (queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY())) {
                                        System.out.println("YOYO " + c);
                                        //queuePiece.get(0).freeMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove){
                                System.out.println(c);
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            if (queuePiece.get(0).captureMoves != null) {
                                for (Coordinate c : queuePiece.get(0).captureMoves) {
                                    if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY()) {
                                        //queuePiece.get(0).captureMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                                queuePiece.get(0).coordinate.getY()) {
                                            //queuePiece.get(0).captureMoves.remove(c);
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove){
                                        pawn.enPassantMoves.remove(c);
                                    }
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            X++;
            Y--;
        }

        //DIAGONALA = DREAPTA-SUS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X++;
        Y++;
        while (X <= 8 && Y <= 8) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Bishop") == 0) {

                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).freeMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    //queuePiece.get(0).captureMoves.remove(c);
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                                queuePiece.get(0).coordinate.getY()) {
                                            //queuePiece.get(0).captureMoves.remove(c);
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove){
                                        pawn.enPassantMoves.remove(c);
                                    }
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            X++;
            Y++;
        }

        //DIAGONALA = STANGA-SUS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X--;
        Y++;
        while (X >= 1 && Y <= 8) {
            pieceFromTableCoordinate = Board.getInstance().getCoordinates(X, Y);
            pieceFromTable = Board.getInstance().getPiecebylocation(pieceFromTableCoordinate);

            if (pieceFromTable != null) {
                if (pieceFromTable.color == OurKing.color) {
                    if (FLAG == 0) {
                        queuePiece.add(pieceFromTable);
                        FLAG = 1;
                    } else {
                        break;
                    }
                } else {
                    if (FLAG == 1) {
                        if (pieceFromTable.getType().compareTo("Queen") == 0 || pieceFromTable.getType().compareTo("Bishop") == 0) {

                            ArrayList<Coordinate> forRemove = new ArrayList<>();
                            if (queuePiece.get(0).freeMoves != null) {
                                for (Coordinate c : queuePiece.get(0).freeMoves) {

                                    if ((c.getIntX() + c.getY()) != (queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY())) {

                                        //queuePiece.get(0).freeMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).freeMoves.remove(c);
                            }

                            forRemove = new ArrayList<>();
                            if (queuePiece.get(0).captureMoves != null) {
                                for (Coordinate c : queuePiece.get(0).captureMoves) {
                                    if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY()) {
                                        //queuePiece.get(0).captureMoves.remove(c);
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove){
                                queuePiece.get(0).captureMoves.remove(c);
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                                queuePiece.get(0).coordinate.getY()) {
                                            //queuePiece.get(0).captureMoves.remove(c);
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove){
                                        pawn.enPassantMoves.remove(c);
                                    }
                                }
                            }

                            invalidPieces.addAll(queuePiece);
                        }
                    }
                    break;
                }
            }
            X--;
            Y++;
        }

    }

    public void addPiece(Piece p) {
        if (p.color == Game.getInstance().enginecolor) {
            defense[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 1;
            enemyattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 2;
            if (p.getType().compareTo("King") == 0) {
                defense[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
                enemyattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
            }
        } else {
            enemyattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 1;
            defense[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 2;
            if (p.getType().compareTo("King") == 0) {
                defense[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
                enemyattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
            }
        }
    }

    //Metoda care trimite xboard-ului o mutare a unui pion
    //in functie de culoarea piesei.
    //In cazul in care nu mai este nicio mutare posibila
    //vom da resign.
    public void doMove() {
        Game game = Game.getInstance();
        Piece piece;
        //Se selecteaza un pion in functie de culoarea engine-ului
        if (game.enginecolor == TeamColor.BLACK) {
            piece = Blacks.getInstance().getPiece();
        } else {
            piece = Whites.getInstance().getPiece();
        }
        System.out.println(piece + " VLAD - " + piece.coordinate + "-" + piece.freeMoves + "-" + piece.captureMoves);
        //Daca nu mai exista pioni cu mutari disponibile
        //atunci dam resign
        if (piece == null) {
            System.out.println("resign");
            System.out.flush();
            return;
        }

        //Se genereaza mutarile posibile
        if (piece.getType() == "Pawn") {
            Pawn pion = (Pawn) piece;
            if (pion.enPassantMoves.size() != 0) {
                Coordinate c = pion.enPassantMoves.get(0);
                //Se scrie la standard output comanda "move + mutarea gasita"
                System.out.println("move " + pion.coordinate.getCharX() + pion.coordinate.getY() +
                        c.getCharX() + c.getY());
                System.out.flush();
                //Se updateaza tabla de joc
                if (pion.color == TeamColor.BLACK) {
                    Whites.getInstance().removeWhitePiece(Board.getInstance().getPiecebylocation(new Coordinate(c.getIntX(), c.getY() + 1)));
                    Board.getInstance().table[9 - c.getY() - 1][c.getIntX()] = null;
                } else {
                    Blacks.getInstance().removeBlackPiece(Board.getInstance().getPiecebylocation(new Coordinate(c.getIntX(), c.getY() - 1)));
                    Board.getInstance().table[9 - c.getY() + 1][c.getIntX()] = null;
                }
                Board.getInstance().executeMove("" + pion.coordinate.getCharX() + pion.coordinate.getY() +
                        c.getCharX() + c.getY());
                return;
            }
        }

        //Am prioritizat mutarile de capturare
        if (piece.captureMoves.size() != 0) {
            Coordinate c = piece.captureMoves.get(0);
            //Se scrie la standard output comanda "move + mutarea gasita"
            if (piece.getType().compareTo("Pawn") == 0) {
                if (piece.color == TeamColor.WHITE && c.getY() == 8) {
                    Pawn pion = (Pawn) piece;
                    char gen = pion.promotionGeneration();
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    System.out.flush();
                    //Se updateaza tabla de joc
                    Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    return;
                }
                if (piece.color == TeamColor.BLACK && c.getY() == 1) {
                    Pawn pion = (Pawn) piece;
                    char gen = pion.promotionGeneration();
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    System.out.flush();
                    //Se updateaza tabla de joc
                    Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    return;
                }
            }
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();
            //Se updateaza tabla de joc
            Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }

        //Mutari libere
        if (piece.freeMoves.size() != 0) {
            Coordinate c = piece.freeMoves.get(0);
            //Se scrie la standard output comanda "move + mutarea gasita"
            if (piece.getType().compareTo("Pawn") == 0) {
                if (piece.color == TeamColor.WHITE && c.getY() == 8) {
                    Pawn pion = (Pawn) piece;
                    char gen = pion.promotionGeneration();
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    System.out.flush();
                    //Se updateaza tabla de joc
                    Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    return;
                }
                if (piece.color == TeamColor.BLACK && c.getY() == 1) {
                    Pawn pion = (Pawn) piece;
                    char gen = pion.promotionGeneration();
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    System.out.flush();
                    //Se updateaza tabla de joc
                    Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    return;
                }

            }
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();
            //Se updateaza tabla de joc
            Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }

        System.out.println("resign");
        System.out.flush();
    }

    Piece checkChess() {
        Board b = Board.getInstance();
        Game game = Game.getInstance();
        if (game.enginecolor == TeamColor.BLACK) {
            for (Piece p : Whites.getInstance().whites) {
                if (p.captureMoves.contains(Blacks.getInstance().getKingLocation())) {
                    return p;
                }
            }
        } else {
            for (Piece p : Blacks.getInstance().blacks) {
                if (p.captureMoves.contains(Whites.getInstance().getKingLocation())) {
                    return p;
                }
            }
        }
        return null;
    }

    void captureChessPiece(Piece p) {
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            for (Piece piece : Blacks.getInstance().blacks) {
                if (piece.captureMoves.contains(p.coordinate)) {
                    if (piece.getType().compareTo("King") != 0) {
                        if (piece.getType().compareTo("Pawn") != 0) {
                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            System.out.flush();
                            //Se updateaza tabla de joc
                            Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            return;
                        } else {
                            if (piece.color == TeamColor.WHITE && p.coordinate.getY() == 8) {
                                Pawn pion = (Pawn) piece;
                                char gen = pion.promotionGeneration();
                                System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                System.out.flush();
                                //Se updateaza tabla de joc
                                Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                return;
                            }
                            if (piece.color == TeamColor.BLACK && p.coordinate.getY() == 1) {
                                Pawn pion = (Pawn) piece;
                                char gen = pion.promotionGeneration();
                                System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                System.out.flush();
                                //Se updateaza tabla de joc
                                Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

}

