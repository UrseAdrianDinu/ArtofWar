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
        this.value=200;
    }

    public King(int x, int y, int teamColor) {
        this.coordinate = new Coordinate(x, y);
        color = teamColor;
    }

    public King(Coordinate coordinate, int color, ArrayList<Coordinate> freemoves,
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

    public King(King king) {
        this(king.coordinate,king.color, king.freeMoves, king.captureMoves, king.value, king.moves, king.support, king.turns);
    }


    // Metoda care genereaza mutarile posible pentru rege
    // La fiecare apel vectorii freeMoves/captureMoves se reinitializeaza
    public void generateMoves(Board board) {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();

        int x = coordinate.getIntX();
        int y = coordinate.getY();
        int type; // type arata ce tip de mutare este

        if (turns != Game.getInstance().gameturns) {
            turns = Game.getInstance().gameturns;
            support = 0;
        }

        String rocada = Brain.getInstance().checkCastlingconditions(board, color);
        if (rocada.compareTo("") != 0) {
            // In functie de culoarea engine-ului se executa rocada mica/mare
            String[] castlings = rocada.split(" ");
            if (castlings[0].compareTo("mica") == 0) {
                if (color == TeamColor.BLACK) {
                    freeMoves.add(board.getCoordinates(7, 8));
                    //board.executeMove("h8f8");
                } else {
                    //System.out.println("move e1g1");
                    freeMoves.add(board.getCoordinates(7, 1));
                    //board.executeMove("e1g1");
                    //board.executeMove("h1f1");
                    //return;
                }
            } else {
                if (color == TeamColor.BLACK) {
                    //System.out.println("move e8c8");
                    freeMoves.add(board.getCoordinates(3, 8));
                    //board.executeMove("e8c8");
                    //board.executeMove("a8d8");
                    //return;
                } else {
//                      System.out.println("move e1c1");
                    freeMoves.add(board.getCoordinates(3, 1));
//                    board.executeMove("e1c1");
//                    board.executeMove("a1d1");
//                    return;
                }
            }
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

    public int getTypeint(){
        return Scores.KING;
    }
    @Override
    public String toString() {
        if (color == 0)
            return "WKing ";
        return "BKing ";
    }
}
