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

        if (x + 1 <= 8) {
            for (int i = x + 1; i <= 8; i++) {
                int type = board.isEmpty(board.getCoordinates(i, y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, y));
                }
            }
        }

        if (x - 1 >= 0) {
            for (int i = x - 1; i >= 0; i--) {
                int type = board.isEmpty(board.getCoordinates(i, y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, y));
                }
            }
        }

        if (y + 1 <= 8) {
            for (int i = y + 1; i <= 8; i++) {
                int type = board.isEmpty(board.getCoordinates(x, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x, i));
                }
            }
        }

        if (y - 1 >= 0) {
            for (int i = y - 1; i >= 0; i--) {
                int type = board.isEmpty(board.getCoordinates(x, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x, i));
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
