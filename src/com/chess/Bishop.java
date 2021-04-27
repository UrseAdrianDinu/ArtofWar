package com.chess;

import java.util.ArrayList;

/*
    Clasa specifica piesei "Nebun"
 */
public class Bishop extends Piece {

    //Initializare coordonata si culoare
    public Bishop(Coordinate coordinate, int color) {
        this.color = color;
        this.coordinate = coordinate;
    }

    @Override
    public void generateMoves() {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int X = coordinate.getIntX();
        int Y = coordinate.getY();
        Board board = Board.getInstance();

        int x = X;
        int y = Y;
        while (x + 1 <= 8 && y + 1 <= 8) {

            int type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x + 1, y + 1));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y + 1));
                break;
            }
            if (type == Move.BLOCK) {
                break;
            }
            x++;
            y++;
        }

        x = X;
        y = Y;
        if (x - 1 >= 1 && y + 1 <= 8) {
            while (x - 1 >= 1 && y + 1 <= 8) {
                int type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x - 1, y + 1));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x - 1, y + 1));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
                x--;
                y++;
            }
        }
        x = X;
        y = Y;
        if (x + 1 <= 8 && y - 1 >= 1) {
            while (x + 1 <= 8 && y - 1 >= 1) {
                int type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x + 1, y - 1));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x + 1, y - 1));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
                x++;
                y--;
            }
        }
        x = X;
        y = Y;
        if (x - 1 >= 1 && y - 1 >= 1) {
            while (x - 1 >= 1 && y - 1 >= 1) {
                int type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x - 1, y - 1));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x - 1, y - 1));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
                x--;
                y--;
            }
        }


    }

    @Override
    public String getType() {
        return "Bishop";
    }

    @Override
    public String toString() {
        if (color == 0)
            return "WBishop ";
        return "BBishop ";
    }
}
