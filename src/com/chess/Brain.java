package com.chess;

import java.util.ArrayList;

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
    int[][] engineattack;
    int[][] defense;
    ArrayList<Piece> invalidPieces = new ArrayList<>();
    int engineChessNumber = 0;
    int enemyChessNumber = 0;
    int enginesquares = 0;
    int enemysquares = 0;

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
        engineattack = new int[9][9];
        enginesquares = 0;
        enemysquares = 0;
        // Generare mutari pentru culoarea alba
        // Updatam matricea enemyattack
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            for (Piece p : board.whites) {
                p.generateMoves(board);
                addPiece(p);
                if (p.getType().compareTo("Pawn") != 0) {
                    for (Coordinate c : p.freeMoves) {
                        if (enemyattack[9 - c.getY()][c.getIntX()] == 0) {
                            enemyattack[9 - c.getY()][c.getIntX()] = 4;
                            enemysquares++;
                        } else {
                            if (enemyattack[9 - c.getY()][c.getIntX()] >= 4) {
                                enemyattack[9 - c.getY()][c.getIntX()]++;
                                enemysquares++;
                            }
                        }
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() + 1 <= 8) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] == 0) {
                                enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] = 4;
                                enemysquares++;
                            } else {
                                if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] >= 4) {
                                    enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1]++;
                                    enemysquares++;
                                }
                            }
                    }
                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() + 1 <= 8) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] == 0) {
                                enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] = 4;
                                enemysquares++;
                            } else {
                                if (enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] >= 4) {
                                    enemyattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1]++;
                                    enemysquares++;
                                }
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

                if (p.getType().compareTo("Pawn") != 0) {
                    for (Coordinate c : p.freeMoves) {
                        if (engineattack[9 - c.getY()][c.getIntX()] == 0) {
                            engineattack[9 - c.getY()][c.getIntX()] = 4;
                            enginesquares++;
                        } else {
                            if (engineattack[9 - c.getY()][c.getIntX()] >= 4) {
                                engineattack[9 - c.getY()][c.getIntX()]++;
                                enemysquares++;
                            }
                        }
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() - 1 >= 1) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            if (engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] == 0) {
                                engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] = 4;
                                enginesquares++;
                            } else {
                                if (engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] >= 4) {
                                    engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1]++;
                                    enginesquares++;
                                }
                            }
                    }

                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() - 1 >= 1) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            if (engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] == 0) {
                                engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] = 4;
                                enginesquares++;
                            } else {
                                if (engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] >= 4) {
                                    engineattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1]++;
                                    enginesquares++;
                                }
                            }
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
                        if (enemyattack[9 - c.getY()][c.getIntX()] == 0) {
                            enemyattack[9 - c.getY()][c.getIntX()] = 4;
                            enemysquares++;
                        } else {
                            if (enemyattack[9 - c.getY()][c.getIntX()] >= 4) {
                                enemyattack[9 - c.getY()][c.getIntX()]++;
                                enemysquares++;
                            }
                        }
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() - 1 >= 1) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] == 0) {
                                enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] = 4;
                                enemysquares++;
                            } else {
                                if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] >= 4) {
                                    enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1]++;
                                    enemysquares++;
                                }
                            }
                    }
                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() - 1 >= 1) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() - 1), p.color);
                        if (type == Move.FREE)
                            if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] == 0) {
                                enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] = 4;
                                enemysquares++;
                            } else {
                                if (enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] >= 4) {
                                    enemyattack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1]++;
                                    enemysquares++;
                                }
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

                if (p.getType().compareTo("Pawn") != 0) {
                    for (Coordinate c : p.freeMoves) {
                        if (engineattack[9 - c.getY()][c.getIntX()] == 0) {
                            engineattack[9 - c.getY()][c.getIntX()] = 4;
                            enginesquares++;
                        } else {
                            if (engineattack[9 - c.getY()][c.getIntX()] >= 4) {
                                engineattack[9 - c.getY()][c.getIntX()]++;
                                enemysquares++;
                            }
                        }
                    }
                } else {
                    if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() + 1 <= 8) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            if (engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] == 0) {
                                engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] = 4;
                                enginesquares++;
                            } else {
                                if (engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] >= 4) {
                                    engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1]++;
                                    enginesquares++;
                                }
                            }
                    }

                    if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() + 1 <= 8) {
                        int type = board.isEmpty(board.getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() + 1), p.color);
                        if (type == Move.FREE)
                            if (engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] == 0) {
                                engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] = 4;
                                enginesquares++;
                            } else {
                                if (engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] >= 4) {
                                    engineattack[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1]++;
                                    enginesquares++;
                                }
                            }
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
            OurKing = board.getBlackKing();
        } else {
            OurKing = board.getWhiteKing();
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
            engineattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 1;
            if (p.getType().compareTo("King") == 0) {
                defense[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
                enemyattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
                engineattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
            }
        } else {
            enemyattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 1;
            defense[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 2;
            engineattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 2;
            if (p.getType().compareTo("King") == 0) {
                defense[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
                enemyattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
                engineattack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 3;
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
            //System.out.println(piece + " " + piece.coordinate);
        } else {
            piece = board.getWhitePiece();
            //System.out.println(piece + " " + piece.coordinate);
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
            //  System.out.println("nunununun");
            for (Piece p : b.whites) {
                if (p.captureMoves.contains(b.getBlackKingLocation())) {
                    pieces.add(p);
                }
            }
        } else {
            //  System.out.println("dadadadad");
            for (Piece p : b.blacks) {
                if (p.captureMoves.contains(b.getWhiteKingLocation())) {
                    pieces.add(p);
                }
            }
        }
        return pieces;
    }

    ArrayList<Piece> checkChessEvaluation(Board b, int color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        if (color == TeamColor.BLACK) {
            //System.out.println("nunununun");
            for (Piece p : b.whites) {

                //System.out.println("kingBlack: " + b.getBlackKingLocation());
                if (p.captureMoves.contains(b.getBlackKingLocation())) {
                    pieces.add(p);
                }
            }
        } else {
            //   System.out.println("dadadadad");
            for (Piece p : b.blacks) {
                //System.out.println("kingWhite: " + b.getWhiteKingLocation());
                if (p.captureMoves.contains(b.getWhiteKingLocation())) {
                    pieces.add(p);
                }
            }
        }
        //System.out.println();
        return pieces;
    }

    // Metoda care incearca sa capture piesa p care da sah engine-ului
    // In acest caz returnand true, altfel false
    ArrayList<String> captureChessPiece(Piece p, Board board) {
        ArrayList<String> moves = new ArrayList<>();
        if (p.color == TeamColor.WHITE) {
            for (Piece piece : board.blacks) {
                if (piece.captureMoves.contains(p.coordinate)) {
                    if (piece.getType().compareTo("King") != 0) {
                        if (piece.getType().compareTo("Pawn") != 0) {
                            // Se scrie la standard output comanda "move + mutarea gasita"
                            //System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            //       p.coordinate.getCharX() + p.coordinate.getY());
                            //System.out.flush();
                            //Se updateaza tabla de joc
                            //board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            //        p.coordinate.getCharX() + p.coordinate.getY());
                            //return true;
                            String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY();
                            moves.add(s);
                        } else {
                            if (p.coordinate.getY() == 1) {
                                Pawn pion = (Pawn) piece;
                                char gen = pion.promotionGeneration();
                                // Se scrie la standard output comanda "move + mutarea gasita"
//                                System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
//                                System.out.flush();
//                                //Se updateaza tabla de joc
//                                board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
                                //return true;
                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen;
                                moves.add(s);
                            }
                            // Se scrie la standard output comanda "move + mutarea gasita"
//                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            System.out.flush();
//                            //Se updateaza tabla de joc
//                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            return true;
                            String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY();
                            moves.add(s);
                        }
                    } else {
                        if (p.support == 0) {
                            // Se scrie la standard output comanda "move + mutarea gasita"
//                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            System.out.flush();
//                            //Se updateaza tabla de joc
//                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            return true;
                            String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY();
                            moves.add(s);
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
//                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            System.out.flush();
//                            //Se updateaza tabla de joc
//                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            return true;
                            String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY();
                            moves.add(s);
                        } else {
                            if (p.coordinate.getY() == 8) {
                                Pawn pion = (Pawn) piece;
                                char gen = pion.promotionGeneration();
                                // Se scrie la standard output comanda "move + mutarea gasita"
//                                System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
//                                System.out.flush();
//                                //Se updateaza tabla de joc
//                                board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                        p.coordinate.getCharX() + p.coordinate.getY() + gen);
//                                return true;
                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                        p.coordinate.getCharX() + p.coordinate.getY() + gen;
                                moves.add(s);
                            }
                            // Se scrie la standard output comanda "move + mutarea gasita"
//                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            System.out.flush();
//                            //Se updateaza tabla de joc
//                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            return true;
                            String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY();
                            moves.add(s);
                        }
                    } else {
                        if (p.support == 0) {
//                            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            System.out.flush();
//                            //Se updateaza tabla de joc
//                            board.executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
//                                    p.coordinate.getCharX() + p.coordinate.getY());
//                            return true;
                            String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                    p.coordinate.getCharX() + p.coordinate.getY();
                            moves.add(s);
                        }

                    }
                }
            }
        }
        return moves;
    }

    // Metoda care incearca sa protejeze regele aflat in sah
    // Returneaza true, in cazul in care a gasit o mutare
    // Returneaza false, in cazul in care am primit sah-mat
    ArrayList<String> protectKing(ArrayList<Piece> pieces, Board board) {
        // Cazul in care primim sah de la o singura piesa
        ArrayList<String> moves = new ArrayList<>();
        if (pieces.size() == 1) {
            Piece p = pieces.get(0);
            if (p.color == TeamColor.BLACK) {
                Coordinate kingloc = board.getWhiteKingLocation();
                if (p.getType().equals("Bishop")) {
                    moves.addAll(captureChessPiece(p, board));

                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll((moveKing));
                    if (moveKing.size() == 0) {
                        for (Coordinate c : p.freeMoves) {
                            if (c.getIntX() + c.getY() == kingloc.getIntX() + kingloc.getY() ||
                                    Math.abs(c.getIntX() - kingloc.getIntX()) == Math.abs(c.getY() - kingloc.getY())) {
                                if (kingloc.getY() > p.coordinate.getY()) {
                                    if (c.getY() > p.coordinate.getY()) {
                                        for (Piece piece : board.whites) {
                                            if (piece.getType().compareTo("King") != 0) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (c.getY() < p.coordinate.getY()) {
                                        for (Piece piece : board.whites) {
                                            if (piece.getType().compareTo("King") != 0) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (p.getType().equals("Queen")) {
                    moves.addAll(captureChessPiece(p, board));

                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll((moveKing));
                    if (moveKing.size() == 0) {
                        for (Coordinate c : p.freeMoves) {
                            if (c.getIntX() == kingloc.getIntX()) {
                                if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                        || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                    for (Piece piece : board.whites) {
                                        if (!piece.getType().equals("King")) {
                                            if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
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
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
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
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (c.getY() < p.coordinate.getY()) {
                                        for (Piece piece : board.whites) {
                                            if (piece.getType().compareTo("King") != 0) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (p.getType().equals("Knight")) {
                    moves.addAll(captureChessPiece(p, board));
                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                }

                if (p.getType().equals("Rook")) {
                    moves.addAll(captureChessPiece(p, board));
                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                    if (moveKing.size() == 0) {
                        for (Coordinate c : p.freeMoves) {
                            if (c.getIntX() == kingloc.getIntX()) {
                                if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                        || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                    for (Piece piece : board.whites) {
                                        if (!piece.getType().equals("King")) {
                                            if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
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
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (p.getType().equals("Pawn")) {
                    moves.addAll(captureChessPiece(p, board));
                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                }
            } else {
                Coordinate kingloc = board.getBlackKingLocation();
                // System.out.println("King location" + kingloc);
                if (p.getType().equals("Bishop")) {
                    moves.addAll(captureChessPiece(p, board));
                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                    if (moveKing.size() == 0) {
                        for (Coordinate c : p.freeMoves) {
                            if (c.getIntX() + c.getY() == kingloc.getIntX() + kingloc.getY() ||
                                    Math.abs(c.getIntX() - kingloc.getIntX()) == Math.abs(c.getY() - kingloc.getY())) {
                                if (kingloc.getY() > p.coordinate.getY()) {
                                    if (c.getY() > p.coordinate.getY()) {
                                        for (Piece piece : board.blacks) {
                                            if (piece.getType().compareTo("King") != 0) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (c.getY() < p.coordinate.getY()) {
                                        for (Piece piece : board.blacks) {
                                            if (piece.getType().compareTo("King") != 0) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (p.getType().equals("Queen")) {
                    moves.addAll(captureChessPiece(p, board));
                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                    if (moveKing.size() == 0) {
                        for (Coordinate c : p.freeMoves) {
                            if (c.getIntX() == kingloc.getIntX()) {
                                if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                        || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                    for (Piece piece : board.blacks) {
                                        if (!piece.getType().equals("King")) {
                                            if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
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
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
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
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (c.getY() < p.coordinate.getY()) {
                                        for (Piece piece : board.blacks) {
                                            if (piece.getType().compareTo("King") != 0) {
                                                if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                    String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                            c.getCharX() + c.getY();
                                                    moves.add(s);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                if (p.getType().equals("Knight")) {
                    moves.addAll(captureChessPiece(p, board));
                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                }

                if (p.getType().equals("Rook")) {
                    moves.addAll(captureChessPiece(p, board));

                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                    if (moveKing.size() == 0) {
                        for (Coordinate c : p.freeMoves) {
                            if (c.getIntX() == kingloc.getIntX()) {
                                if ((c.getY() > p.coordinate.getY() && p.coordinate.getY() < kingloc.getY())
                                        || (c.getY() < p.coordinate.getY() && p.coordinate.getY() > kingloc.getY())) {
                                    for (Piece piece : board.blacks) {
                                        if (!piece.getType().equals("King")) {
                                            if (piece.freeMoves.contains(c) || piece.captureMoves.contains(c)) {
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
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
                                                String s = "" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                                                        c.getCharX() + c.getY();
                                                moves.add(s);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (p.getType().equals("Pawn")) {
                    moves.addAll(captureChessPiece(p, board));
                    ArrayList<String> moveKing = moveKing(p, board);
                    moves.addAll(moveKing);
                }
            }
        } else {

            // Cazul in care primim sah de la doua piese
            ArrayList<String> moves1 = moveKing(pieces.get(0), board);
            ArrayList<String> moves2 = moveKing(pieces.get(1), board);
            ArrayList<String> intersection = new ArrayList<>();
            for (String c : moves1) {
                if (moves2.contains(c)) {
                    intersection.add(c);
                }
            }

            moves.addAll(intersection);
        }
        return moves;
    }

    // Metoda care intoarce un vector de coordonate
    // pe care regele aflat in sah se poate muta
    ArrayList<String> moveKing(Piece attackingPiece, Board board) {
        Coordinate kingloc;
        Piece ourking;
        if (attackingPiece.color == TeamColor.WHITE) {
            kingloc = board.getBlackKingLocation();
            ourking = board.getBlackKing();
        } else {
            kingloc = board.getWhiteKingLocation();
            ourking = board.getWhiteKing();
        }
        ArrayList<String> moves = new ArrayList<>();
        //DE VERIFICAT TURA/REGINA.
        //DREAPTA
        if (kingloc.getIntX() + 1 <= 8) {
            if (enemyattack[9 - kingloc.getY()][kingloc.getIntX() + 1] == 0) {
                if (attackingPiece.getType().compareTo("Queen") == 0 || attackingPiece.getType().compareTo("Rook") == 0) {
                    if (attackingPiece.coordinate.getY() == kingloc.getY()) {
                        //nu face nimic.
                    } else {
                        Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY());
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY());
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY()));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY());
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
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
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY());
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY()));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY());
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
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
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() + 1);
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX(), kingloc.getY() + 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() + 1);
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
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
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() - 1);
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX(), kingloc.getY() - 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX(), kingloc.getY() - 1);
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
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
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() + 1);
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                    ;
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() + 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() + 1);
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
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
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() + 1);
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() + 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() + 1);
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
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
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() - 1);
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() - 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() + 1, kingloc.getY() - 1);
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
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
                        String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                        moves.add(s);
                    }
                } else {
                    Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() - 1);
                    String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                    moves.add(s);
                }
            } else {
                Piece p = board.getPiecebylocation(board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() - 1));
                if (p != null) {
                    if (p.color != ourking.color) {
                        if (p.support == 0) {
                            Coordinate c = board.getCoordinates(kingloc.getIntX() - 1, kingloc.getY() - 1);
                            String s = "" + kingloc.getCharX() + kingloc.getY() + c.getCharX() + c.getY();
                            moves.add(s);
                        }
                    }
                }
            }
        }
        return moves;
    }

    // Metoda care intoarce un string format din
    // rocadele care se pot efectua
    String checkCastlingconditions(Board board, int color) {
        String s = "";
        if (color == TeamColor.BLACK) {
            if (board.getBlackKing().moves == 0) {
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
            if (board.getWhiteKing().moves == 0) {
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
//        System.out.println("STARI");
        // System.out.println(board);
        ArrayList<String> stari = new ArrayList<>();
        ArrayList<Piece> chess = Brain.getInstance().checkChessEvaluation(board, color);
        if (chess.size() > 0) {
            ArrayList<String> movesProtect = Brain.getInstance().protectKing(chess, board);
            stari.addAll(movesProtect);
        } else {
//            System.out.println("COLOR " + color);
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

//        for (int i = 0; i < stari.size(); i++) {
//            System.out.println("-------------------------");
//            System.out.println(i);
//            System.out.println(stari.get(i));
//        }
        return stari;
    }

    //    int spaceEval(int color)
//    {
//
//    }
    public Pair alphabeta(Board board, String lastmove, int depth, int alpha, int beta, boolean MaximizingPlayer, int nrsaheng, int nrsahadv) throws CloneNotSupportedException {
        //System.out.println(board);

        Brain.getInstance().generateAllMoves(board);

        if (depth == 0) {
            Pair pa = new Pair(lastmove, board.evaluateBoard(MaximizingPlayer));
            //System.out.println("ZERO" + pa);
            // System.out.println(pa.c + " " + pa.scor + "\n" + board);
            return pa;
        }

        if (MaximizingPlayer) {
            //System.out.println("MAXI");
            Pair maxmove = new Pair("", Integer.MIN_VALUE);
            Pair p = null;
            ArrayList<String> stari = stari(board, Game.getInstance().enginecolor);

            if (stari.size() == 0) {
                return new Pair(lastmove, -100000);
            }

            for (String s : stari) {
                Board copy = board.copie();
                //System.out.println("AFISDASDAS");
                // System.out.println(board.engineChessNumber);
                //System.out.println(board.enemyChessNumber);
                copy.executeMove(s);
                //System.out.println("DUPA execute MAX: " + copy.evaluateBoard(MaximizingPlayer));
                p = alphabeta(copy, s, depth - 1, alpha, beta, false, nrsaheng, nrsahadv);

                if (checkChessEvaluation(copy, Game.getInstance().usercolor).size() > 0) {
                    if (nrsaheng == 2) {
                        p.scor = 100000;
                    } else {
                        p.scor += 400 + nrsaheng * 120;
                    }
                }

                if (p.scor > maxmove.scor) {
                    //System.out.println("MAXIMIMIMIM");
                    //System.out.println(p);
                    maxmove.scor = p.scor;
                    maxmove.c = s;
                }

                alpha = Math.max(alpha, p.scor);


                //System.out.println("MAXMOVE " + maxmove);
                if (beta <= alpha)
                    break;
            }
            return maxmove;
        } else {
            // System.out.println("MINI");
            Pair minmove = new Pair("", Integer.MAX_VALUE);
            ArrayList<String> stari = stari(board, Game.getInstance().usercolor);
            if (stari.size() == 0) {
                return new Pair(lastmove, -100000);
            }
            Pair p = null;
            for (String s : stari) {
                Board copy = board.copie();
                copy.executeMove(s);
                // System.out.println("AFISDASDAS");
                //System.out.println(board.engineChessNumber);
                //System.out.println(board.enemyChessNumber);
                //System.out.println("DUPA execute MIN: " + copy.evaluateBoard(MaximizingPlayer));
                p = alphabeta(copy, s, depth - 1, alpha, beta, true, nrsaheng, nrsahadv);
                //System.out.println("MINMOVE" + p.c + " " + p.scor);

                if (checkChessEvaluation(copy, Game.getInstance().enginecolor).size() > 0) {
                    if (nrsahadv == 2) {
                        p.scor = -100000;
                    } else {
                        p.scor -= 400 - 120 * nrsahadv;
                    }
                }

                if (p.scor < minmove.scor) {
                    minmove.c = s;
                    minmove.scor = p.scor;
                }

                beta = Math.min(beta, p.scor);

                //System.out.println("MINMOVE " + minmove);
                if (beta <= alpha)
                    break;
            }

            return minmove;
        }

    }

}