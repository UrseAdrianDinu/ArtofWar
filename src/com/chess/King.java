package com.chess;

import java.util.ArrayList;
/*
    Clasa specifica piesei "Rege"
 */

public class King extends Piece {

    // Initializare coordonata si culoare
    public King(Coordinate coordinate, int teamColor) {
        this.coordinate = coordinate;
        color = teamColor;
    }

    public King(int x, int y, int teamColor) {
        this.coordinate = new Coordinate(x, y);
        color = teamColor;
    }

    // Metoda care genereaza mutarile posible pentru rege
    // La fiecare apel vectorii freeMoves/captureMoves se reinitializeaza
    public void generateMoves() {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        Board board = Board.getInstance();
        int x = coordinate.getIntX();
        int y = coordinate.getY();
        int type; // type arata ce tip de mutare este

        if (turns != Game.getInstance().gameturns) {
            turns = Game.getInstance().gameturns;
            support = 0;
        }

        // Verificam daca regele se poate muta pe casuta din dreapta
        if (x + 1 <= 8) {
            type = board.isEmpty(board.getCoordinates(x + 1, y), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x + 1, y));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }

        // Verificam daca regele se poate muta pe casuta din stanga
        if (x - 1 >= 1) {
            type = board.isEmpty(board.getCoordinates(x - 1, y), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x - 1, y));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }

        // Verificam daca regele se poate muta pe casuta din fata lui
        if (y + 1 <= 8) {
            type = board.isEmpty(board.getCoordinates(x, y + 1), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x, y + 1));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x, y + 1));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x, y + 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }

        // Verificam daca regele se poate muta pe casuta din spatele lui
        if (y - 1 >= 1) {
            type = board.isEmpty(board.getCoordinates(x, y - 1), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x, y - 1));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x, y - 1));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x, y - 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }

        // Verificam daca regele se poate muta pe casuta dreapta-sus
        if (x + 1 <= 8 && y + 1 <= 8) {
            type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y + 1));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x + 1, y + 1));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y + 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }

        // Verificam daca regele se poate muta pe casuta dreapta-jos
        if (x + 1 <= 8 && y - 1 >= 1) {
            type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y - 1));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x + 1, y - 1));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y - 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }

        // Verificam daca regele se poate muta pe casuta stanga-jos
        if (x - 1 >= 1 && y - 1 >= 1) {
            type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y - 1));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x - 1, y - 1));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y - 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }

        // Verificam daca regele se poate muta pe casuta stanga-sus
        if (x - 1 >= 1 && y + 1 <= 8) {
            type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y + 1));
            } else {
                if (type != Move.BLOCK)
                    freeMoves.add(board.getCoordinates(x - 1, y + 1));
                else {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y + 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }
        }
    }

    @Override
    public String getType() {
        return "King";
    }

    @Override
    public String toString() {
        if (color == 0)
            return "WKing ";
        return "BKing ";
    }
}
