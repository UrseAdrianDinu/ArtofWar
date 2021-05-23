package com.chess;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;

/*
    Clasa care implementeaza gandirea engine-ului.
    Pentru moment, ea decide mutarile pionilor.
    Parametrii clasei:
        enemyattack - matrice care retine unde poate sa atace inamicul
                      si pozitiile pieselor
        defense - matrice care retine unde se poate apara engine-ul
                     si pozitiile pieselor
        invalidPieces - vector de piese ale caror miscari sunt
                    sunt invalide
        blackKing - regele negru
        whiteKing - regele alb
 */
public class Brain {

    int[][] enemyattack;
    int[][] defense;
    ArrayList<Piece> invalidPieces = new ArrayList<>();
    Piece blackKing;
    Piece whiteKing;

    // Singleton Pattern
    private static Brain instance = null;

    private Brain() {

    }

    public static Brain getInstance() {
        if (instance == null)
            instance = new Brain();
        return instance;
    }

    // Metoda care genereaza toate miscarile posibile
    // pentru ambele culori si updateaza matricele defense si enemyattack
    void generateAllMoves(Board board) {
        defense = new int[9][9];
        enemyattack = new int[9][9];

        // Generare mutari pentru culoarea alba
        // Updatam matricea enemyattack
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            for (Piece p : board.whites) {
                p.generateMoves(board);
                addPiece(p);
                if (p.getType().compareTo("Pawn") != 0) {
                    for (Coordinate c : p.freeMoves) {
                        if (enemyattack[9 - c.getY()][c.getIntX()] == 0)
                            enemyattack[9 - c.getY()][c.getIntX()] = 4;
                        else {
                            if (enemyattack[9 - c.getY()][c.getIntX()] >= 4)
                                enemyattack[9 - c.getY()][c.getIntX()]++;
                        }
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() + 1 <= 8) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] == 0)
                                enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] = 4;
                            else {
                                if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] >= 4)
                                    enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1]++;
                            }
                    }
                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() + 1 <= 8) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] == 0)
                                enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] = 4;
                            else {
                                if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] >= 4)
                                    enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1]++;
                            }
                    }
                }
            }

            // Generare mutari pentru culoarea neagra
            // Updatam matricea defense
            for (Piece p : board.blacks) {
                p.generateMoves(board);
                addPiece(p);
                for (Coordinate c : p.freeMoves) {
                    if (defense[9 - c.getY()][c.getIntX()] == 0)
                        defense[9 - c.getY()][c.getIntX()] = 4;
                    else {
                        if (defense[9 - c.getY()][c.getIntX()] >= 4)
                            defense[9 - c.getY()][c.getIntX()]++;
                    }
                }

            }
        } else {
            // Generare mutari pentru culoarea neagra
            // Updatam matricea enemyattack
            for (Piece p : board.blacks) {
                p.generateMoves(board);
                addPiece(p);
                if (p.getType().compareTo("Pawn") != 0) {
                    for (Coordinate c : p.freeMoves) {
                        if (enemyattack[9 - c.getY()][c.getIntX()] == 0)
                            enemyattack[9 - c.getY()][c.getIntX()] = 4;
                        else {
                            if (enemyattack[9 - c.getY()][c.getIntX()] >= 4)
                                enemyattack[9 - c.getY()][c.getIntX()]++;
                        }
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() - 1 >= 1) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] == 0)
                                enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] = 4;
                            else {
                                if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] >= 4)
                                    enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1]++;
                            }
                    }
                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() - 1 >= 1) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] == 0)
                                enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] = 4;
                            else {
                                if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] >= 4)
                                    enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1]++;
                            }
                    }
                }
            }
            // Generare mutari pentru culoarea alba
            // Updatam matricea defense
            for (Piece p : board.whites) {
                p.generateMoves(board);
                addPiece(p);
                for (Coordinate c : p.freeMoves) {
                    if (defense[9 - c.getY()][c.getIntX()] == 0)
                        defense[9 - c.getY()][c.getIntX()] = 4;
                    else {
                        if (defense[9 - c.getY()][c.getIntX()] >= 4)
                            defense[9 - c.getY()][c.getIntX()]++;
                    }
                }

            }
        }

        // Eliminare miscari invalide
        verifyInvalidMoves(board);
    }

    // Metoda care elimina miscarile invalide
    public void verifyInvalidMoves(Board board) {
        Piece OurKing;
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            OurKing = blackKing;
        } else {
            OurKing = whiteKing;
        }

        invalidPieces = new ArrayList<>();
        Coordinate KingCoordinate = OurKing.coordinate;
        Piece pieceFromTable;
        Coordinate pieceFromTableCoordinate;
        ArrayList<Piece> queuePiece = new ArrayList<>();
        ArrayList<Coordinate> forRemove = new ArrayList<>();

        // LINIE - SUS
        int X = KingCoordinate.getIntX();
        int Y = KingCoordinate.getY();
        Y++;
        int FLAG = 0;
        while (Y <= 8) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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
                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove) {
                                    pawn.enPassantMoves.remove(c);
                                    if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                        defense[9 - c.getY()][c.getIntX()] = 0;
                                    }
                                    if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                        defense[9 - c.getY()][c.getIntX()]--;
                                    }
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

        // LINIE - JOS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        Y--;
        queuePiece = new ArrayList<>();
        FLAG = 0;
        while (Y >= 1) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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
                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getIntX() != queuePiece.get(0).coordinate.getIntX()) {
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove) {
                                    pawn.enPassantMoves.remove(c);
                                    if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                        defense[9 - c.getY()][c.getIntX()] = 0;
                                    }
                                    if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                        defense[9 - c.getY()][c.getIntX()]--;
                                    }
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

        // LINIE - DREAPTA
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X++;
        while (X <= 8) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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
                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove) {
                                    pawn.enPassantMoves.remove(c);
                                    if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                        defense[9 - c.getY()][c.getIntX()] = 0;
                                    }
                                    if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                        defense[9 - c.getY()][c.getIntX()]--;
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
        }

        // LINIE - STANGA
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X--;
        while (X >= 1) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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
                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                forRemove = new ArrayList<>();
                                for (Coordinate c : pawn.enPassantMoves) {
                                    if (c.getY() != queuePiece.get(0).coordinate.getY()) {
                                        forRemove.add(c);
                                    }
                                }
                                for (Coordinate c : forRemove) {
                                    pawn.enPassantMoves.remove(c);
                                    if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                        defense[9 - c.getY()][c.getIntX()] = 0;
                                    }
                                    if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                        defense[9 - c.getY()][c.getIntX()]--;
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
        }

        // DIAGONALA = STANGA-JOS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X--;
        Y--;
        while (X >= 1 && Y >= 1) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                                queuePiece.get(0).coordinate.getY()) {
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove) {
                                        pawn.enPassantMoves.remove(c);
                                        if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                            defense[9 - c.getY()][c.getIntX()] = 0;
                                        }
                                        if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                            defense[9 - c.getY()][c.getIntX()]--;
                                        }
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

        // DIAGONALA = DREAPTA-JOS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X++;
        Y--;
        while (X <= 8 && Y >= 1) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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
                            forRemove = new ArrayList<>();
                            if (queuePiece.get(0).freeMoves != null) {
                                for (Coordinate c : queuePiece.get(0).freeMoves) {
                                    if ((c.getIntX() + c.getY()) != (queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY())) {
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            if (queuePiece.get(0).captureMoves != null) {
                                for (Coordinate c : queuePiece.get(0).captureMoves) {
                                    if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY()) {
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                                queuePiece.get(0).coordinate.getY()) {
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove) {
                                        pawn.enPassantMoves.remove(c);
                                        if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                            defense[9 - c.getY()][c.getIntX()] = 0;
                                        }
                                        if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                            defense[9 - c.getY()][c.getIntX()]--;
                                        }
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

        // DIAGONALA = DREAPTA-SUS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X++;
        Y++;
        while (X <= 8 && Y <= 8) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).freeMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            for (Coordinate c : queuePiece.get(0).captureMoves) {
                                if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                        queuePiece.get(0).coordinate.getY()) {
                                    forRemove.add(c);
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() - c.getY() != queuePiece.get(0).coordinate.getIntX() -
                                                queuePiece.get(0).coordinate.getY()) {
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove) {
                                        pawn.enPassantMoves.remove(c);
                                        if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                            defense[9 - c.getY()][c.getIntX()] = 0;
                                        }
                                        if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                            defense[9 - c.getY()][c.getIntX()]--;
                                        }
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

        // DIAGONALA = STANGA-SUS
        X = KingCoordinate.getIntX();
        Y = KingCoordinate.getY();
        queuePiece = new ArrayList<>();
        FLAG = 0;
        X--;
        Y++;
        while (X >= 1 && Y <= 8) {
            pieceFromTableCoordinate = board.getCoordinates(X, Y);
            pieceFromTable = board.getPiecebylocation(pieceFromTableCoordinate);

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

                            forRemove = new ArrayList<>();
                            if (queuePiece.get(0).freeMoves != null) {
                                for (Coordinate c : queuePiece.get(0).freeMoves) {

                                    if ((c.getIntX() + c.getY()) != (queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY())) {
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).freeMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            forRemove = new ArrayList<>();
                            if (queuePiece.get(0).captureMoves != null) {
                                for (Coordinate c : queuePiece.get(0).captureMoves) {
                                    if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                            queuePiece.get(0).coordinate.getY()) {
                                        forRemove.add(c);
                                    }
                                }
                            }

                            for (Coordinate c : forRemove) {
                                queuePiece.get(0).captureMoves.remove(c);
                                if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                    defense[9 - c.getY()][c.getIntX()] = 0;
                                }
                                if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                    defense[9 - c.getY()][c.getIntX()]--;
                                }
                            }

                            if (queuePiece.get(0).getType().compareTo("Pawn") == 0) {
                                Pawn pawn = (Pawn) queuePiece.get(0);
                                if (pawn.enPassantMoves != null) {
                                    forRemove = new ArrayList<>();
                                    for (Coordinate c : pawn.enPassantMoves) {
                                        if (c.getIntX() + c.getY() != queuePiece.get(0).coordinate.getIntX() +
                                                queuePiece.get(0).coordinate.getY()) {
                                            forRemove.add(c);
                                        }
                                    }

                                    for (Coordinate c : forRemove) {
                                        pawn.enPassantMoves.remove(c);
                                        if (defense[9 - c.getY()][c.getIntX()] == 4) {
                                            defense[9 - c.getY()][c.getIntX()] = 0;
                                        }
                                        if (defense[9 - c.getY()][c.getIntX()] > 4) {
                                            defense[9 - c.getY()][c.getIntX()]--;
                                        }
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

        // Eliminare miscari libere invalide pentru rege
        for (Coordinate c : OurKing.freeMoves) {
            if (enemyattack[9 - c.getY()][c.getIntX()] >= 4) {
                forRemove.add(c);
            }
        }

        for (Coordinate c : forRemove) {
            OurKing.freeMoves.remove(c);
            if (defense[9 - c.getY()][c.getIntX()] == 4) {
                defense[9 - c.getY()][c.getIntX()] = 0;
            }
            if (defense[9 - c.getY()][c.getIntX()] > 4) {
                defense[9 - c.getY()][c.getIntX()]--;
            }
        }

        // Eliminare miscari de capturare invalide pentru rege
        forRemove = new ArrayList<>();
        for (Coordinate c : OurKing.captureMoves) {
            Piece p = board.getPiecebylocation(c);
            if (p.support != 0) {
                forRemove.add(c);
            }
        }
        for (Coordinate c : forRemove) {
            OurKing.captureMoves.remove(c);
        }

    }

    // Metoda care adauga o piesa p pe matricele defense/enemyattack
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

    // Metoda care trimite xboard-ului o mutare a unei piese
    // in functie de culoarea sa.
    // In cazul in care nu mai este nicio mutare posibila
    // vom da resign.
    public void doMove(Board board) {
        Game game = Game.getInstance();
        Piece piece;

        // Se selecteaza o piesa in functie de culoarea engine-ului
        if (game.enginecolor == TeamColor.BLACK) {
            piece = board.getBlackPiece();
            System.out.println(piece + " " + piece.coordinate);
        } else {
            piece = board.getWhitePiece();
            System.out.println(piece + " " + piece.coordinate);
        }

        // Daca nu mai exista piese cu mutari disponibile
        // atunci dam resign
        if (piece == null) {
            System.out.println("resign");
            System.out.flush();
            return;
        }

        // Am verificat daca piesa selectata este pion
        // In acest caz am prioritizat miscarile en-passant

        if (piece.getType().equals("Pawn")) {
            Pawn pion = (Pawn) piece;
            if (pion.enPassantMoves.size() != 0) {
                Coordinate c = pion.enPassantMoves.get(0);

                // Se scrie la standard output comanda "move + mutarea gasita"
                System.out.println("move " + pion.coordinate.getCharX() + pion.coordinate.getY() +
                        c.getCharX() + c.getY());
                System.out.flush();

                // Se updateaza tabla de joc
                if (pion.color == TeamColor.BLACK) {
                    board.whites.remove(board.getPiecebylocation(new Coordinate(c.getIntX(), c.getY() + 1)));
                    board.table[9 - c.getY() - 1][c.getIntX()] = null;
                } else {
                    board.blacks.remove(board.getPiecebylocation(new Coordinate(c.getIntX(), c.getY() - 1)));
                    board.table[9 - c.getY() + 1][c.getIntX()] = null;
                }
                board.executeMove("" + pion.coordinate.getCharX() + pion.coordinate.getY() +
                        c.getCharX() + c.getY());
                return;
            }
        }

        // Am prioritizat mutarile de capturare
        if (piece.captureMoves.size() != 0) {
            Coordinate c = piece.captureMoves.get(0);
            // Am verificat daca piesa selectata este pion
            // si daca prin mutarea gasita ajunge pe ultima linie
            // In acest caz, am facut promovorea lui
            if (piece.getType().compareTo("Pawn") == 0) {
                if (piece.color == TeamColor.WHITE && c.getY() == 8) {
                    Pawn pion = (Pawn) piece;
                    char gen = pion.promotionGeneration();
                    // Se scrie la standard output comanda "move + mutarea gasita"
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    System.out.flush();
                    //Se updateaza tabla de joc
                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    return;
                }
                if (piece.color == TeamColor.BLACK && c.getY() == 1) {
                    Pawn pion = (Pawn) piece;
                    char gen = pion.promotionGeneration();
                    // Se scrie la standard output comanda "move + mutarea gasita"
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    System.out.flush();
                    //Se updateaza tabla de joc
                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    return;
                }
            }
            // Cazul in care piesa selectata nu e pion
            // Se scrie la standard output comanda "move + mutarea gasita"
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();
            //Se updateaza tabla de joc
            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }

        //Mutari libere
        if (piece.freeMoves.size() != 0) {
            Coordinate c = piece.freeMoves.get(0);
            // Am verificat daca piesa selectata este pion
            // si daca prin mutarea gasita ajunge pe ultima linie
            // In acest caz, am facut promovorea lui
            if (piece.getType().compareTo("Pawn") == 0) {
                if (piece.color == TeamColor.WHITE && c.getY() == 8) {
                    Pawn pion = (Pawn) piece;
                    char gen = pion.promotionGeneration();
                    // Se scrie la standard output comanda "move + mutarea gasita"
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    System.out.flush();
                    //Se updateaza tabla de joc
                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
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
                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY() + gen);
                    return;
                }

            }
            // Cazul in care piesa selectata nu e pion
            // Se scrie la standard output comanda "move + mutarea gasita"
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();
            //Se updateaza tabla de joc
            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }
        System.out.println("resign");
        System.out.flush();
    }

    // Metoda care intoarce o lista de piese care dau sah engine-ului
    ArrayList<Piece> checkChess(Board b) {
        Game game = Game.getInstance();
        ArrayList<Piece> pieces = new ArrayList<>();
        if (game.enginecolor == TeamColor.BLACK) {
            for (Piece p : b.whites) {
                if (p.captureMoves.contains(b.getBlackKingLocation())) {
                    pieces.add(p);
                }
            }
        } else {
            for (Piece p : b.blacks) {
                if (p.captureMoves.contains(b.getWhiteKingLocation())) {
                    pieces.add(p);
                }
            }
        }
        return pieces;
    }

    // Metoda care incearca sa capture piesa p care da sah engine-ului
    // In acest caz returnand true, altfel false
    boolean captureChessPiece(Piece p, Board board) {
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            for (Piece piece : board.blacks) {
                if (piece.captureMoves.contains(p.coordinate)) {
                    if (piece.getType().compareTo("King") != 0) {
                        if (piece.getType().compareTo("Pawn") != 0) {
                            // Se scrie la standard output comanda "move + mutarea gasita"
                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            System.out.flush();
                            //Se updateaza tabla de joc
                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            return true;
                        } else {
                            if (p.coordinate.getY() == 1) {
                                Pawn pion = (Pawn) piece;
                                char gen = pion.promotionGeneration();
                                // Se scrie la standard output comanda "move + mutarea gasita"
                                System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                System.out.flush();
                                //Se updateaza tabla de joc
                                board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                return true;
                            }
                            // Se scrie la standard output comanda "move + mutarea gasita"
                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            System.out.flush();
                            //Se updateaza tabla de joc
                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            return true;
                        }
                    } else {
                        if (p.support == 0) {
                            // Se scrie la standard output comanda "move + mutarea gasita"
                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            System.out.flush();
                            //Se updateaza tabla de joc
                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            return true;
                        }

                    }
                }
            }
        } else {
            for (Piece piece : board.whites) {
                if (piece.captureMoves.contains(p.coordinate)) {
                    if (piece.getType().compareTo("King") != 0) {
                        if (piece.getType().compareTo("Pawn") != 0) {
                            // Se scrie la standard output comanda "move + mutarea gasita"
                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            System.out.flush();
                            //Se updateaza tabla de joc
                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            return true;
                        } else {
                            if (p.coordinate.getY() == 8) {
                                Pawn pion = (Pawn) piece;
                                char gen = pion.promotionGeneration();
                                // Se scrie la standard output comanda "move + mutarea gasita"
                                System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                System.out.flush();
                                //Se updateaza tabla de joc
                                board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                return true;
                            }
                            // Se scrie la standard output comanda "move + mutarea gasita"
                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            System.out.flush();
                            //Se updateaza tabla de joc
                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            return true;
                        }
                    } else {
                        if (p.support == 0) {
                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            System.out.flush();
                            //Se updateaza tabla de joc
                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY());
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

    // Metoda care incearca sa protejeze regele aflat in sah
    // Returneaza true, in cazul in care a gasit o mutare
    // Returneaza false, in cazul in care am primit sah-mat
    boolean protectKing(ArrayList<Piece> pieces, Board board) {
        // Cazul in care primim sah de la o singura piesa
        if (pieces.size() == 1) {
            Piece p = pieces.get(0);
            if (p.color == TeamColor.BLACK) {
                Coordinate kingloc = board.getWhiteKingLocation();
                if (p.getType().equals("Bishop")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            for (Coordinate c : p.freeMoves) {
                                if (c.getIntX() + c.getY() == kingloc.getIntX() + kingloc.getY() ||
                                        Math.abs(c.getIntX() - kingloc.getIntX()) == Math.abs(c.getY() - kingloc.getY())) {
                                    if (kingloc.getY() > p.coordinate.getY()) {
                                        if (c.getY() > p.coordinate.getY()) {
                                            for (Piece piece : board.whites) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (c.getY() < p.coordinate.getY()) {
                                            for (Piece piece : board.whites) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }
                if (p.getType().equals("Queen")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            for (Coordinate c : p.freeMoves) {
                                if (c.getIntX() == kingloc.getIntX()) {
                                    if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                            || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                        for (Piece piece : board.whites) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (c.getY() == kingloc.getY()) {
                                    if ((c.getIntX() > p.coordinate.getIntX() && p.coordinate.getIntX() < kingloc.getIntX())
                                            || (c.getIntX() < p.coordinate.getIntX() && p.coordinate.getIntX() > kingloc.getIntX())) {
                                        for (Piece piece : board.whites) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (c.getIntX() + c.getY() == kingloc.getIntX() + kingloc.getY() ||
                                        Math.abs(c.getIntX() - kingloc.getIntX()) == Math.abs(c.getY() - kingloc.getY())) {
                                    if (kingloc.getY() > p.coordinate.getY()) {
                                        if (c.getY() > p.coordinate.getY()) {
                                            for (Piece piece : board.whites) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (c.getY() < p.coordinate.getY()) {
                                            for (Piece piece : board.whites) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }

                if (p.getType().equals("Knight")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            System.out.println("resign");
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }

                if (p.getType().equals("Rook")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            for (Coordinate c : p.freeMoves) {
                                if (c.getIntX() == kingloc.getIntX()) {
                                    if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                            || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                        for (Piece piece : board.whites) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (c.getY() == kingloc.getY()) {
                                    if ((c.getIntX() > p.coordinate.getIntX() && p.coordinate.getIntX() < kingloc.getIntX())
                                            || (c.getIntX() < p.coordinate.getIntX() && p.coordinate.getIntX() > kingloc.getIntX())) {
                                        for (Piece piece : board.whites) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }

                if (p.getType().equals("Pawn")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            System.out.println("resign");
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }


            } else {
                Coordinate kingloc = board.getBlackKingLocation();
                System.out.println("King location" + kingloc);
                if (p.getType().equals("Bishop")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            for (Coordinate c : p.freeMoves) {
                                if (c.getIntX() + c.getY() == kingloc.getIntX() + kingloc.getY() ||
                                        Math.abs(c.getIntX() - kingloc.getIntX()) == Math.abs(c.getY() - kingloc.getY())) {
                                    if (kingloc.getY() > p.coordinate.getY()) {
                                        if (c.getY() > p.coordinate.getY()) {
                                            for (Piece piece : board.blacks) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (c.getY() < p.coordinate.getY()) {
                                            for (Piece piece : board.blacks) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }

                if (p.getType().equals("Queen")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            for (Coordinate c : p.freeMoves) {
                                if (c.getIntX() == kingloc.getIntX()) {
                                    if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                            || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                        for (Piece piece : board.blacks) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (c.getY() == kingloc.getY()) {
                                    if ((c.getIntX() > p.coordinate.getIntX() && p.coordinate.getIntX() < kingloc.getIntX())
                                            || (c.getIntX() < p.coordinate.getIntX() && p.coordinate.getIntX() > kingloc.getIntX())) {
                                        for (Piece piece : board.blacks) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (c.getIntX() + c.getY() == kingloc.getIntX() + kingloc.getY() ||
                                        Math.abs(c.getIntX() - kingloc.getIntX()) == Math.abs(c.getY() - kingloc.getY())) {
                                    if (kingloc.getY() > p.coordinate.getY()) {
                                        if (c.getY() > p.coordinate.getY()) {
                                            for (Piece piece : board.blacks) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (c.getY() < p.coordinate.getY()) {
                                            for (Piece piece : board.blacks) {
                                                if (piece.getType().compareTo("King") != 0) {
                                                    if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                        System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        System.out.flush();
                                                        board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                                c.getCharX() + c.getY());
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }


                if (p.getType().equals("Knight")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            System.out.println("resign");
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }

                if (p.getType().equals("Rook")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            for (Coordinate c : p.freeMoves) {
                                if (c.getIntX() == kingloc.getIntX()) {
                                    if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                            || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                        for (Piece piece : board.blacks) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (c.getY() == kingloc.getY()) {
                                    if ((c.getIntX() > p.coordinate.getIntX() && p.coordinate.getIntX() < kingloc.getIntX())
                                            || (c.getIntX() < p.coordinate.getIntX() && p.coordinate.getIntX() > kingloc.getIntX())) {
                                        for (Piece piece : board.blacks) {
                                            if (!piece.getType().equals("King")) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    System.out.flush();
                                                    board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY());
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }

                if (p.getType().equals("Pawn")) {
                    boolean capture = captureChessPiece(p, board);
                    if (capture) {
                        return true;
                    } else {
                        ArrayList<Coordinate> moveKing = moveKing(p, board);
                        if (moveKing.size() == 0) {
                            System.out.println("resign");
                        } else {
                            Coordinate c = moveKing.get(0);
                            System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            System.out.flush();
                            board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                                    c.getCharX() + c.getY());
                            return true;
                        }
                    }
                }

            }
        } else {

            // Cazul in care primim sah de la doua piese
            ArrayList<Coordinate> moves1 = moveKing(pieces.get(0), board);
            ArrayList<Coordinate> moves2 = moveKing(pieces.get(1), board);
            ArrayList<Coordinate> intersection = new ArrayList<>();
            for (Coordinate c : moves1) {
                if (moves2.contains(c)) {
                    intersection.add(c);
                }
            }
            if (intersection.size() == 0) {
                System.out.println("resign");
            } else {
                Coordinate kingloc;
                if (pieces.get(0).color == TeamColor.BLACK) {
                    kingloc = board.getWhiteKingLocation();
                } else {
                    kingloc = board.getBlackKingLocation();
                }
                Coordinate c = intersection.get(0);
                System.out.println("move " + kingloc.getCharX() + kingloc.getY() +
                        c.getCharX() + c.getY());
                System.out.flush();
                board.executeMove("" + kingloc.getCharX() + kingloc.getY() +
                        c.getCharX() + c.getY());
                return true;
            }
        }
        return false;
    }

    // Metoda care intoarce un vector de coordonate
    // pe care regele aflat in sah se poate muta
    ArrayList<Coordinate> moveKing(Piece attackingPiece, Board board) {
        Coordinate kingloc;
        Piece ourking;
        if (attackingPiece.color == TeamColor.WHITE) {
            kingloc = blackKing.coordinate;
            ourking = blackKing;
        } else {
            kingloc = whiteKing.coordinate;
            ourking = whiteKing;
        }
        ArrayList<Coordinate> coord = new ArrayList<>();
        //DE VERIFICAT TURA/REGINA.
        //DREAPTA
        if (kingloc.getIntX() + 1 <= 8) {
            if (enemyattack[9 - kingloc.getY()][kingloc.getIntX() + 1] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Rook") == 0) {
                    if (attackingPiece.coordinate.getY() == kingloc.getY()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY());
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY());
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY()));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY());
                            coord.add(c);
                        }
                    }
                }
            }
        }

        //STANGA
        if (kingloc.getIntX() - 1 >= 1) {
            if (enemyattack[9 - kingloc.getY()][kingloc.getIntX() - 1] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Rook") == 0) {
                    if (attackingPiece.coordinate.getY() == kingloc.getY()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY());
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY());
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY()));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY());
                            coord.add(c);
                        }
                    }
                }
            }
        }

        //SUS
        if (kingloc.getY() + 1 <= 8) {
            if (enemyattack[9 - kingloc.getY() - 1][kingloc.getIntX()] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Rook") == 0) {
                    if (attackingPiece.coordinate.getIntX() == kingloc.getIntX()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() + 1);
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() + 1);
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX(), kingloc.getY() + 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() + 1);
                            coord.add(c);
                        }
                    }
                }
            }
        }


        //JOS
        if (kingloc.getY() - 1 >= 1) {
            if (enemyattack[9 - kingloc.getY() + 1][kingloc.getIntX()] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Rook") == 0) {
                    if (attackingPiece.coordinate.getIntX() == kingloc.getIntX()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() - 1);
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() - 1);
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX(), kingloc.getY() - 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() - 1);
                            coord.add(c);
                        }
                    }
                }
            }
        }

        //DIAGDRSUS
        if (kingloc.getY() + 1 <= 8 && kingloc.getIntX() + 1 <= 8) {
            if (enemyattack[9 - kingloc.getY() - 1][kingloc.getIntX() + 1] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Bishop") == 0) {
                    if (attackingPiece.coordinate.getIntX() - attackingPiece.coordinate.getY() == kingloc.getIntX() - kingloc.getY()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() + 1);
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() + 1);
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() + 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() + 1);
                            coord.add(c);
                        }
                    }
                }
            }
        }

        //DIAGSTSUS
        if (kingloc.getY() + 1 <= 8 && kingloc.getIntX() - 1 >= 1) {
            if (enemyattack[9 - kingloc.getY() - 1][kingloc.getIntX() - 1] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Bishop") == 0) {
                    if (attackingPiece.coordinate.getIntX() + attackingPiece.coordinate.getY() == kingloc.getIntX() + kingloc.getY()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() + 1);
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() + 1);
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() + 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() + 1);
                            coord.add(c);
                        }
                    }
                }
            }
        }

        //DIAGDRJOS
        if (kingloc.getY() - 1 >= 1 && kingloc.getIntX() + 1 <= 8) {
            if (enemyattack[9 - kingloc.getY() + 1][kingloc.getIntX() + 1] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Bishop") == 0) {
                    if (attackingPiece.coordinate.getIntX() + attackingPiece.coordinate.getY() == kingloc.getIntX() + kingloc.getY()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() - 1);
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() - 1);
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() - 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() - 1);
                            coord.add(c);
                        }
                    }
                }
            }
        }

        //DIAGSTJOS
        if (kingloc.getY() - 1 >= 1 && kingloc.getIntX() - 1 >= 1) {
            if (enemyattack[9 - kingloc.getY() + 1][kingloc.getIntX() - 1] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Bishop") == 0) {
                    if (attackingPiece.coordinate.getIntX() - attackingPiece.coordinate.getY() == kingloc.getIntX() - kingloc.getY()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() - 1);
                        coord.add(c);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() - 1);
                    coord.add(c);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() - 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() - 1);
                            coord.add(c);
                        }
                    }
                }
            }
        }
        return coord;
    }

    // Metoda care intoarce un string format din
    // rocadele care se pot efectua
    String checkCastlingconditions(Board board) {
        String s = "";
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            if (blackKing.moves == 0) {
                Piece p1 = board.getPiecebylocation(board.getCoordinates(8, 8));
                if (p1 != null) {
                    if (p1.moves == 0 && p1.color == TeamColor.BLACK) {
                        if (enemyattack[1][6] == 0 && enemyattack[1][7] == 0) {
                            s += "mica ";
                        }
                    }
                }

                Piece p2 = board.getPiecebylocation(board.getCoordinates(1, 8));
                Piece p3 = board.getPiecebylocation(board.getCoordinates(2, 8));
                if (p2 != null) {
                    if (p2.moves == 0 && p2.color == TeamColor.BLACK) {
                        if (enemyattack[1][3] == 0 && enemyattack[1][4] == 0 && p3 == null) {
                            s += " mare";
                        }
                    }
                }
            }
        } else {
            if (whiteKing.moves == 0) {
                Piece p1 = board.getPiecebylocation(board.getCoordinates(8, 1));
                if (p1 != null) {
                    if (p1.moves == 0 && p1.color == TeamColor.WHITE) {
                        if (enemyattack[8][6] == 0 && enemyattack[8][7] == 0) {
                            s += "mica ";
                        }
                    }
                }

                Piece p2 = board.getPiecebylocation(board.getCoordinates(1, 1));
                Piece p3 = board.getPiecebylocation(board.getCoordinates(1, 2));
                if (p2 != null) {
                    if (p2.moves == 0 && p2.color == TeamColor.WHITE) {
                        if (enemyattack[8][3] == 0 && enemyattack[8][4] == 0 && p3 == null) {
                            s += "mare";
                        }
                    }
                }

            }
        }
        return s;
    }

    ArrayList<String> stari(Board board, int color) throws CloneNotSupportedException {
        System.out.println("STARI");
        System.out.println(board);
        ArrayList<String> stari = new ArrayList<>();
        if (checkChess(board).size() > 0) {

        } else {
            System.out.println("COLOR " + color);
            if (color == TeamColor.BLACK) {
                for (Piece p : board.blacks) {


                    for (Coordinate c : p.freeMoves) {
                        //Board copy = board.copie();
                        String s = "" + p.coordinate.getCharX() + p.coordinate.getY() +
                                c.getCharX() + c.getY();
                        //copy.executeMove(s);
                        stari.add(s);
                    }

                    for (Coordinate c : p.captureMoves) {
                        //Board copy = board.copie();
                        String s = "" + p.coordinate.getCharX() + p.coordinate.getY() +
                                c.getCharX() + c.getY();
                        //copy.executeMove(s);
                        stari.add(s);
                    }

                    if (p.getType().equals("Pawn")) {
                        Pawn pion = (Pawn) p;
                        for (Coordinate c : pion.enPassantMoves) {
                            //Board copy = board.copie();
                            String s = "" + p.coordinate.getCharX() + p.coordinate.getY() +
                                    c.getCharX() + c.getY();
                            //copy.executeMove(s);
                            stari.add(s);
                        }
                    }
                }
            } else {
                for (Piece p : board.whites) {

                    for (Coordinate c : p.freeMoves) {
                        //Board copy = board.copie();
                        String s = "" + p.coordinate.getCharX() + p.coordinate.getY() +
                                c.getCharX() + c.getY();
                        //copy.executeMove(s);
                        stari.add(s);
                    }

                    for (Coordinate c : p.captureMoves) {
                        //Board copy = board.copie();
                        String s = "" + p.coordinate.getCharX() + p.coordinate.getY() +
                                c.getCharX() + c.getY();
                        //copy.executeMove(s);
                        stari.add(s);
                    }

                    if (p.getType().equals("Pawn")) {
                        Pawn pion = (Pawn) p;
                        for (Coordinate c : pion.enPassantMoves) {
                            //Board copy = board.copie();
                            String s = "" + p.coordinate.getCharX() + p.coordinate.getY() +
                                    c.getCharX() + c.getY();
                            //copy.executeMove(s);
                            stari.add(s);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < stari.size(); i++) {
            System.out.println("-------------------------");
            System.out.println(i);
            System.out.println(stari.get(i));
        }
        return stari;
    }


    public Pair alphabeta(Board board, String lastmove, int depth, int alpha, int beta, boolean MaximizingPlayer) throws CloneNotSupportedException {
        System.out.println(board);
        if (depth == 0) {
            return new Pair(lastmove, board.evaluateBoard());
        }

        if (MaximizingPlayer) {

            Pair maxmove = new Pair("", Integer.MIN_VALUE);
            Pair p = null;
            Brain.getInstance().generateAllMoves(board);
            ArrayList<String> stari = stari(board, Game.getInstance().enginecolor);
            for (String s : stari) {

                Board copy = board.copie();
                System.out.println("Am trimis " + s);
                copy.executeMove(s);
                Brain.getInstance().generateAllMoves(copy);
                for (int i = 1; i <= 8; i++) {
                    for (int j = 1; j <= 8; j++) {
                        if (copy.table[i][j] != null)
                            System.out.println(copy.table[i][j].coordinate);
                    }
                }
//                System.out.println("BLACKS");
//                for (Piece piece : copy.blacks) {
//                    System.out.println(piece.coordinate);
//                }
//
//                System.out.println("WHITES");
//                for (Piece piece : copy.whites) {
//                    System.out.println(piece.coordinate);
//                }

                System.out.println("----------------------------");
                System.out.println(copy);
                p = alphabeta(copy, s, depth - 1, alpha, beta, false);
                if (p.scor >= beta) {
                    return new Pair(maxmove.c, beta);
                }

                if (p.scor >= alpha) {
                    alpha = p.scor;
                    maxmove.c = s;
                }
            }
            return new Pair(maxmove.c, alpha);
        } else {
            Pair minmove = new Pair("", Integer.MAX_VALUE);
            Brain.getInstance().generateAllMoves(board);
            ArrayList<String> stari = stari(board, Game.getInstance().usercolor);
            Pair p = null;
            for (String s : stari) {
                Board copy = board.copie();
                System.out.println("Am trimis " + s);
                copy.executeMove(s);
                Brain.getInstance().generateAllMoves(copy);

                for (int i = 1; i <= 8; i++) {
                    for (int j = 1; j <= 8; j++) {
                        if (copy.table[i][j] != null)
                            System.out.println(copy.table[i][j].coordinate);
                    }
                }
//                System.out.println("BLACKS");
//                for (Piece piece : copy.blacks) {
//                    System.out.println(piece.coordinate);
//                }
//                System.out.println("WHITES");
//                for (Piece piece : copy.whites) {
//                    System.out.println(piece.coordinate);
//                }
                System.out.println("----------------------------");
                System.out.println(copy);
                p = alphabeta(copy, s, depth - 1, alpha, beta, true);
                if (p.scor <= alpha) {
                    return new Pair(minmove.c, alpha);
                }
                if (p.scor < beta) {
                    beta = p.scor;
                    minmove.c = s;
                }
            }
            return new Pair(minmove.c, beta);
        }

    }

}

