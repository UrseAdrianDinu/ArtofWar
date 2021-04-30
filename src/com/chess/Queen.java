package com.chess;

import java.util.ArrayList;

/*
    Clasa specifica piesei "Regina"
 */
public class Queen extends Piece {

    //Initializare coordonata si culoare
    public Queen(Coordinate coordinate, int teamColor) {
        this.coordinate = coordinate;
        color = teamColor;
    }

    @Override
    public void generateMoves() {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int X = coordinate.getIntX();
        int Y = coordinate.getY();
        Board board = Board.getInstance();

        if (X + 1 <= 8) {
            for (int i = X + 1; i <= 8; i++) {
                int type = board.isEmpty(board.getCoordinates(i, Y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, Y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, Y));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
            }
        }

        if (X - 1 >= 1) {
            for (int i = X - 1; i >= 1; i--) {
                int type = board.isEmpty(board.getCoordinates(i, Y), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(i, Y));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(i, Y));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
            }
        }

        if (Y + 1 <= 8) {
            for (int i = Y + 1; i <= 8; i++) {
                int type = board.isEmpty(board.getCoordinates(X, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(X, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(X, i));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
            }
        }

        if (Y - 1 >= 1) {
            for (int i = Y - 1; i >= 1; i--) {
                int type = board.isEmpty(board.getCoordinates(X, i), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(X, i));
                }
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(X, i));
                    break;
                }
                if (type == Move.BLOCK) {
                    break;
                }
            }
        }

        int x = X;
        int y = Y;
        if (x + 1 <= 8 && y + 1 <= 8) {
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
        return "Queen";
    }

    @Override
    public String toString() {
        if (color == 0)
            return "WQueen ";
        return "BQueen ";
    }
}
