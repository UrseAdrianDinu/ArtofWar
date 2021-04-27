package com.chess;

/*
    Clasa care implementeaza gandirea engine-ului.
    Pentru moment, ea decide mutarile pionilor.
 */
public class Brain {

    //Singleton Pattern
    private static Brain instance = null;
    int[][] attackWhite;
    int[][] attackBlack;

    private Brain() {

    }

    public static Brain getInstance() {
        if (instance == null)
            instance = new Brain();
        return instance;
    }

    void generateAllMoves() {
        attackBlack = new int[9][9];
        attackWhite = new int[9][9];
        for (Piece p : Whites.getInstance().whites) {
            p.generateMoves();
            addPiece(p);
            if (p.getType().compareTo("Pawn") != 0) {
                for (Coordinate c : p.freeMoves) {
                    attackWhite[9 - c.getY()][c.getIntX()] = 4;
                }
            } else {
                if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() + 1 <= 8) {
                    int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() + 1), p.color);
                    if (type == Move.FREE)
                        attackWhite[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() + 1] = 4;
                }
                if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() + 1 <= 8) {
                    int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() + 1), p.color);
                    if (type == Move.FREE)
                        attackWhite[9 - p.coordinate.getY() - 1][p.coordinate.getIntX() - 1] = 4;
                }
            }
        }

        for (Piece p : Blacks.getInstance().blacks) {
            p.generateMoves();
            addPiece(p);
            if (p.getType().compareTo("Pawn") != 0) {
                for (Coordinate c : p.freeMoves) {
                    attackBlack[9 - c.getY()][c.getIntX()] = 4;
                }
            } else {
                if (p.coordinate.getIntX() + 1 <= 8 && p.coordinate.getY() - 1 >= 1) {
                    int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() + 1, p.coordinate.getY() - 1), p.color);
                    if (type == Move.FREE)
                        attackBlack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() + 1] = 4;
                }
                if (p.coordinate.getIntX() - 1 >= 1 && p.coordinate.getY() - 1 >= 1) {
                    int type = Board.getInstance().isEmpty(Board.getInstance().getCoordinates(p.coordinate.getIntX() - 1, p.coordinate.getY() - 1), p.color);
                    if (type == Move.FREE)
                        attackBlack[9 - p.coordinate.getY() + 1][p.coordinate.getIntX() - 1] = 4;
                }
            }
        }
    }

    public void addPiece(Piece p) {
        if (p.color == TeamColor.BLACK) {
            attackBlack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 1;
            attackWhite[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 2;
        } else {
            attackWhite[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 1;
            attackBlack[9 - p.coordinate.getY()][p.coordinate.getIntX()] = 2;
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

    boolean checkChess() {
        Board b = Board.getInstance();
        Game game = Game.getInstance();
        if (game.enginecolor == TeamColor.BLACK) {
            System.out.println(Blacks.getInstance().getKingLocation());
            for (Piece p : Whites.getInstance().whites) {
                p.generateMoves();
                if (p.captureMoves.contains(Blacks.getInstance().getKingLocation())) {
                    return true;
                }
            }
        } else {
            for (Piece p : Blacks.getInstance().blacks) {
                p.generateMoves();
                if (p.captureMoves.contains(Whites.getInstance().getKingLocation())) {
                    return true;
                }
            }
        }
        return false;
    }

}
