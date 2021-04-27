package com.chess;

import java.util.ArrayList;

/*
    Clasa specifica piesei "Tura"
 */
public class Rook extends Piece {
    //Initializare coordonata si culoare
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

        for (int i = x + 1; i <= 8; i++) {
            if (i <= 8) {
                int type = board.isEmpty(board.getCoordinates(i, y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, y));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
            }
        }


        for (int i = x - 1; i >= 1; i--) {
            if (i >= 1) {
                int type = board.isEmpty(board.getCoordinates(i, y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, y));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
            }
        }

        for (int i = y + 1; i <= 8; i++) {
            if (i <= 8) {
                int type = board.isEmpty(board.getCoordinates(x, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x, i));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
            }
        }


        for (int i = y - 1; i >= 1; i--) {
            if (i >= 1) {
                int type = board.isEmpty(board.getCoordinates(x, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x, i));
                    break;
                }
                if (type == Move.BLOCK) {
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
