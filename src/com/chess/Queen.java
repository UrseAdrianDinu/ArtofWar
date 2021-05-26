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
        this.value = 90;
    }

    public Queen(Coordinate coordinate, int color, ArrayList<Coordinate> freemoves,
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

    public Queen(Queen queen) {
        this(queen.coordinate,queen.color, queen.freeMoves, queen.captureMoves, queen.value, queen.moves, queen.support, queen.turns);
    }

    @Override
    public void generateMoves(Board board) {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int X = coordinate.getIntX();
        int Y = coordinate.getY();

        // Updatam runda piesei
        if (turns != Game.getInstance().gameturns) {
            turns = Game.getInstance().gameturns;
            support = 0;
        }

        // Linie dreapta
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
                    Piece p = board.getPiecebylocation(board.getCoordinates(i, Y));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
            }
        }

        // Linie stanga
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
                    Piece p = board.getPiecebylocation(board.getCoordinates(i, Y));
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
                    Piece p = board.getPiecebylocation(board.getCoordinates(X, i));
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
                    Piece p = board.getPiecebylocation(board.getCoordinates(X, i));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                    break;
                }
            }
        }

        int x = X;
        int y = Y;

        // Diagonala dreapta sus
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
        }

        x = X;
        y = Y;
        // Diagonala stanga sus
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
        // Diagonala dreapta jos
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

        // Diagonala stanga jos
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
        return "Queen";
    }

    public int getTypeint(){
        return Scores.QUEEN;
    }

    @Override
    public String toString() {
        if (color == 0)
            return "WQueen ";
        return "BQueen ";
    }
}
