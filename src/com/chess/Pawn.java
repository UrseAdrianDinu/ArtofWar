package com.chess;

import java.util.ArrayList;
/*
    Clasa specifica piesei "Pion"
 */
public class Pawn extends Piece {

    //Initializare coordonata si culoare
    public Pawn(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public Pawn(int x, int y, int color) {
        coordinate = new Coordinate(x, y);
        this.color = color;
    }

    //Metoda care genereaza mutarile posible pentru pion
    //La fiecare apel vectorii freeMoves/captureMoves se reinitializeaza
    public void generateMoves() {

        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int x = coordinate.getIntX();
        int y = coordinate.getY();
        Board board = Board.getInstance();

        //In functie de culoarea piesei, analizam pozitiile posibile
        //Pionii albi se pot muta vertical in sus
        //Pionii negri se pot muta vertical in jos

        if (color == TeamColor.WHITE) {
            //Verificam daca pionul se poate muta pe casuta din fata lui
            if (y + 1 <= 8) {
                int type = board.isEmpty(board.getCoordinates(x, y + 1), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y + 1));
                }
            }
            //Verificam daca pionul poate captura piesa din diagonala-dreapta
            if (x + 1 <= 8 && y + 1 <= 8) {
                int type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x + 1, y + 1));
                }
            }
            //Verificam daca pionul poate captura piesa din diagonala-stanga
            if (x - 1 >= 0 && y + 1 <= 8) {
                int type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x - 1, y + 1));
                }
            }

        } else {
            //Verificam daca pionul se poate muta pe casuta din fata lui
            if (y - 1 >= 0) {
                int type = board.isEmpty(board.getCoordinates(x, y - 1), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y - 1));
                }
            }

            //Verificam daca pionul poate captura piesa din diagonala-stanga
            if (x + 1 <= 8 && y - 1 >= 0) {
                int type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x + 1, y - 1));
                }
            }

            //Verificam daca pionul poate captura piesa din diagonala-dreapta
            if (x - 1 >= 0 && y - 1 >= 0) {
                int type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x - 1, y - 1));

                }
            }
        }
    }

    //Metoda prin care un pion se transforma in Regina
    public void pawnToQueen() {
        //Se seteaza pe pozitia pionului o noua regina
        Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()] = new Queen(coordinate, color);
        //In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            Whites.getInstance().removeWhitePiece(this);
            Whites.getInstance().addWhitePiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            Blacks.getInstance().removeBlackPiece(this);
            Blacks.getInstance().addBlackPiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
    }

    @Override
    public String getType() {
        return "Pawn";
    }

    public String toString() {
        if (color == 0)
            return "WPawn ";
        return "BPawn ";
    }


}
