package com.chess;

import java.util.ArrayList;

/*
    Clasa specifica piesei "Nebun"
*/
public class Bishop extends Piece {

    // Initializare coordonata si culoare
    public Bishop(Coordinate coordinate, int color) {
        this.color = color;
        this.coordinate = coordinate;
        this.value = 30;
    }

    public Bishop(Coordinate coordinate, int color, ArrayList<Coordinate> freemoves,
                  ArrayList<Coordinate> capturemoves, int value, int moves, int support, int turns) {
        this.color = color;
        this.value = value;
        this.moves = moves;
        this.support = support;
        this.turns = turns;

        this.coordinate = new Coordinate(coordinate.getIntX(), coordinate.getY());

        this.captureMoves = new ArrayList<>();
        for (Coordinate c : capturemoves) {
            this.captureMoves.add(new Coordinate(c.getIntX(), c.getY()));
        }

        this.freeMoves = new ArrayList<>();
        for (Coordinate c : freemoves) {
            this.freeMoves.add(new Coordinate(c.getIntX(), c.getY()));
        }
    }

    public Bishop(Bishop bishop) {
        this(bishop.coordinate, bishop.color, bishop.freeMoves, bishop.captureMoves, bishop.value, bishop.moves, bishop.support, bishop.turns);
    }

    // Metoda care genereaza mutarile posible pentru nebun
    // La fiecare apel vectorii freeMoves/captureMoves se reinitializeaza
    @Override
    public void generateMoves(Board board) {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int X = coordinate.getIntX();
        int Y = coordinate.getY();

        int x = X;
        int y = Y;

        // Updatam runda piesei
        if (turns != Game.getInstance().gameturns) {
            turns = Game.getInstance().gameturns;
            support = 0;
        }

        // Digonala dreapta sus
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
                Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y + 1));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
                break;
            }
            x++;
            y++;
        }

        x = X;
        y = Y;
        // Digonala stanga sus
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
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y + 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
                x--;
                y++;
            }
        }
        x = X;
        y = Y;

        // Digonala dreapta jos
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
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y - 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
                x++;
                y--;
            }
        }
        x = X;
        y = Y;
        // Digonala stanga jos
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
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y - 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
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

    public int getTypeint(){
        return Scores.BISHOP;
    }

    @Override
    public String toString() {
        if (color == 0)
            return "WBishop ";
        return "BBishop ";
    }
}