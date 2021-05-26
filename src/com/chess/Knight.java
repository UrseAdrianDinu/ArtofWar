package com.chess;

/*
    Clasa specifica piesei "Cal"
 */

import java.util.ArrayList;

public class Knight extends Piece {

    //Initializare coordonata si culoare
    public Knight(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
        this.value=30;
    }

    public Knight(Coordinate coordinate, int color, ArrayList<Coordinate> freemoves,
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

    public Knight(Knight knight) {
        this(knight.coordinate,knight.color, knight.freeMoves, knight.captureMoves, knight.value, knight.moves, knight.support, knight.turns);
    }

    public void generateMoves(Board board) {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int x = coordinate.getIntX();
        int y = coordinate.getY();

        // Updatam runda piesei
        if (turns != Game.getInstance().gameturns) {
            turns = Game.getInstance().gameturns;
            support = 0;
        }


        if (x + 1 <= 8 && y + 2 <= 8) {
            int type = board.isEmpty(board.getCoordinates(x + 1, y + 2), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x + 1, y + 2));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y + 2));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y + 2));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }

        if (x + 2 <= 8 && y + 1 <= 8) {
            int type = board.isEmpty(board.getCoordinates(x + 2, y + 1), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x + 2, y + 1));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 2, y + 1));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x + 2, y + 1));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }

        if (x + 2 <= 8 && y - 1 >= 1) {
            int type = board.isEmpty(board.getCoordinates(x + 2, y - 1), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x + 2, y - 1));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 2, y - 1));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x + 2, y - 1));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }

        if (x + 1 <= 8 && y - 2 >= 1) {
            int type = board.isEmpty(board.getCoordinates(x + 1, y - 2), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x + 1, y - 2));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y - 2));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y - 2));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }

        if (x - 1 >= 1 && y - 2 >= 1) {
            int type = board.isEmpty(board.getCoordinates(x - 1, y - 2), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x - 1, y - 2));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y - 2));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y - 2));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }

        if (x - 2 >= 1 && y - 1 >= 1) {
            int type = board.isEmpty(board.getCoordinates(x - 2, y - 1), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x - 2, y - 1));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 2, y - 1));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x - 2, y - 1));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }

        if (x - 2 >= 1 && y + 1 <= 8) {
            int type = board.isEmpty(board.getCoordinates(x - 2, y + 1), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x - 2, y + 1));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 2, y + 1));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x - 2, y + 1));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }

        if (x - 1 >= 1 && y + 2 <= 8) {
            int type = board.isEmpty(board.getCoordinates(x - 1, y + 2), color);
            if (type == Move.FREE) {
                freeMoves.add(board.getCoordinates(x - 1, y + 2));
            }
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y + 2));
            }
            if (type == Move.BLOCK) {
                Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y + 2));
                if (p.turns != Game.getInstance().gameturns) {
                    p.turns = Game.getInstance().gameturns;
                    p.support = 0;
                }
                p.support++;
            }
        }
    }

    @Override
    public String getType() {
        return "Knight";
    }
    public int getTypeint(){
        return Scores.KNIGHT;
    }


    public String toString() {
        if (color == 0)
            return "WKnight ";
        return "BKnight ";
    }
}
