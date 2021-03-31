package com.chess;

import java.util.ArrayList;
/*
    Clasa specifica piesei "Rege"
 */

public class King extends Piece {

    //Initializare coordonata si culoare
    public King(Coordinate coordinate, int teamColor) {
        this.coordinate = coordinate;
        color = teamColor;
    }

    public King(int x, int y, int teamColor) {
        this.coordinate = new Coordinate(x, y);
        color = teamColor;
    }

    //Metoda temporara care genereaza mutarile posible pentru rege
    //La fiecare apel vectorii freeMoves/captureMoves se reinitializeaza
    public void generateMoves() {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        Board board = Board.getInstance();
        int x = coordinate.getIntX();
        int y = coordinate.getY();
        int type; //type arata ce tip de mutare este

        type = board.isEmpty(board.getCoordinates(x + 1, y), color);
        //Verificam daca regele se poate muta pe casuta din dreapta
        if (x + 1 <= 8 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y));
            } else {
                freeMoves.add(board.getCoordinates(x + 1, y));
            }
        }
        //Verificam daca regele se poate muta pe casuta din stanga
        type = board.isEmpty(board.getCoordinates(x - 1, y), color);
        if (x - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y));
            } else {
                freeMoves.add(board.getCoordinates(x - 1, y));
            }
        }
        //Verificam daca regele se poate muta pe casuta din fata lui
        type = board.isEmpty(board.getCoordinates(x, y + 1), color);
        if (y + 1 <= 8 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x, y + 1));
            } else {
                freeMoves.add(board.getCoordinates(x, y + 1));
            }
        }
        //Verificam daca regele se poate muta pe casuta din spatele lui
        type = board.isEmpty(board.getCoordinates(x, y - 1), color);
        if (y - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x, y - 1));
            } else {
                freeMoves.add(board.getCoordinates(x, y - 1));
            }
        }

        //Verificam daca regele se poate muta pe casuta dreapta-sus
        type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
        if (x + 1 <= 8 && y + 1 <= 8 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y + 1));
            } else {
                freeMoves.add(board.getCoordinates(x + 1, y + 1));
            }
        }

        //Verificam daca regele se poate muta pe casuta dreapta-jos
        type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
        if (x + 1 <= 8 && y - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x + 1, y - 1));
            } else {
                freeMoves.add(board.getCoordinates(x + 1, y - 1));
            }
        }

        //Verificam daca regele se poate muta pe casuta stanga-jos
        type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
        if (x - 1 >= 1 && y - 1 >= 1 && type != Move.BLOCK) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y - 1));
            } else {
                freeMoves.add(board.getCoordinates(x - 1, y - 1));
            }
        }
        //Verificam daca regele se poate muta pe casuta stanga-sus
        type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
        if (x - 1 >= 1 && y + 1 <= 8) {
            if (type == Move.CAPTURE) {
                captureMoves.add(board.getCoordinates(x - 1, y + 1));
            } else {
                freeMoves.add(board.getCoordinates(x - 1, y + 1));
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
