package com.chess;

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
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            for (Piece p : Whites.getInstance().whites) {
                p.generateMoves();
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
                p.generateMoves();
                addPiece(p);
                for (Coordinate c : p.freeMoves) {
                    defense[9 - c.getY()][c.getIntX()] = 4;
                }

            }
        } else {
            for (Piece p : Blacks.getInstance().blacks) {
                p.generateMoves();
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
                p.generateMoves();
                addPiece(p);
                for (Coordinate c : p.freeMoves) {
                    defense[9 - c.getY()][c.getIntX()] = 4;
                }

            }
        }
        Piece p = checkChess();
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
