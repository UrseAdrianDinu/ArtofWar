package com.chess;

import java.util.ArrayList;

public class King extends Piece{
    public King(Coordinate coordinate, int teamColor) {
        this.coordinate = coordinate;
        color = teamColor;
    }

    public King(int x, int y, int teamColor) {
        this.coordinate = new Coordinate(x, y);
        color = teamColor;
    }

    @Override
    public String getType() {
        return "com.company.proiect.King";
    }

    @Override
    public void generateMoves() {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        Board board = Board.getInstance();
        int x = coordinate.getIntX();
        int y = coordinate.getY();
        int type;

        type = board.isEmpty(board.getCoordinates(x + 1, y), color);
        if (x + 1 <= 8 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y));
            } else {
                freeMoves.add(board.getCoordinates(x + 1, y));
            }
        }

        type = board.isEmpty(board.getCoordinates(x - 1, y), color);
        if (x - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y));
            } else {
                freeMoves.add(board.getCoordinates(x - 1, y));
            }
        }

        type = board.isEmpty(board.getCoordinates(x, y + 1), color);
        if (y + 1 <= 8 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x, y + 1));
            } else {
                freeMoves.add(board.getCoordinates(x, y + 1));
            }
        }

        type = board.isEmpty(board.getCoordinates(x, y - 1), color);
        if (y - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x, y - 1));
            } else {
                freeMoves.add(board.getCoordinates(x, y - 1));
            }
        }

        type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
        if (x + 1 <= 8 && y + 1 <= 8 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y + 1));
            } else {
                freeMoves.add(board.getCoordinates(x + 1, y + 1));
            }
        }

        type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
        if (x + 1 <= 8 && y - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y - 1));
            } else {
                freeMoves.add(board.getCoordinates(x + 1, y - 1));
            }
        }

        type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
        if (x - 1 >= 1 && y - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y - 1));
            } else {
                freeMoves.add(board.getCoordinates(x - 1, y - 1));
            }
        }
        type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
        if (x - 1 >= 1 && y + 1 <= 8) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y + 1));
            } else {
                freeMoves.add(board.getCoordinates(x - 1, y + 1));
            }
        }
    }
}
