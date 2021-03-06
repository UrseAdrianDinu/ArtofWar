package com.chess;

import java.util.ArrayList;

/*
    Clasa specifica piesei "Tura"
 */
public class Rook extends Piece {

    // Initializare coordonata si culoare
    public Rook(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public void generateMoves() {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int x = coordinate.getIntX();
        int y = coordinate.getY();
        Board board = Board.getInstance();

        // Updatam runda piesei
        if (turns != Game.getInstance().gameturns) {
            turns = Game.getInstance().gameturns;
            support = 0;
        }

        // Dreapta
        if (x + 1 <= 8) {
            for (int i = x + 1; i <= 8; i++) {
                int type = board.isEmpty(board.getCoordinates(i, y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, y));
                    break;
                }
                if (type == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(i, y));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
            }
        }

        // Stanga
        if (x - 1 >= 1) {
            for (int i = x - 1; i >= 1; i--) {
                int type = board.isEmpty(board.getCoordinates(i, y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, y));
                    break;
                }
                if (type == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(i, y));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
            }
        }

        // Sus
        if (y + 1 <= 8) {
            for (int i = y + 1; i <= 8; i++) {
                int type = board.isEmpty(board.getCoordinates(x, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x, i));
                    break;
                }
                if (type == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x, i));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
            }
        }

        // Jos
        if (y - 1 >= 1) {
            for (int i = y - 1; i >= 1; i--) {
                int type = board.isEmpty(board.getCoordinates(x, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x, i));
                    break;
                }
                if (type == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x, i));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
            }
        }
    }

    @Override
    public String getType() {
        return "Rook";
    }

    public String toString() {
        if (color == 0)
            return "WRook ";
        return "BRook ";
    }
}
