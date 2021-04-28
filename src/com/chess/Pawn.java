package com.chess;

import java.util.ArrayList;
import java.util.Random;

/*
    Clasa specifica piesei "Pion"
 */
public class Pawn extends Piece {
    ArrayList<Coordinate> enPassantMoves;

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
        enPassantMoves = new ArrayList<>();
        int x = coordinate.getIntX();
        int y = coordinate.getY();
        Board board = Board.getInstance();

        //In functie de culoarea piesei, analizam pozitiile posibile
        //Pionii albi se pot muta vertical in sus
        //Pionii negri se pot muta vertical in jos

        if (color == TeamColor.WHITE) {
            if (y + 2 <= 8 && moves == 0) {
                int type1 = board.isEmpty(board.getCoordinates(x, y + 2), color);
                int type2 = board.isEmpty(board.getCoordinates(x, y + 1), color);
                if (type1 == Move.FREE && type2 == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y + 2));
                }
            }
            //Verificam daca pionul se poate muta pe casuta din fata lui
            if (y + 1 <= 8) {
                int type = board.isEmpty(board.getCoordinates(x, y + 1), color);

                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y + 1));
                }
            }
            //Verificam daca pionul poate captura piesa din diagonala-dreapta
            if (x + 1 <= 8 && y + 1 <= 8) {
                int typecapture = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
                if (typecapture == Move.CAPTURE) {
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

            if (y == 5) {
                if (x + 1 <= 8) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y));
                    int type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
                    if (p != null) {
                        if (p.color != this.color && Blacks.getInstance().lastMoved == p && p.moves == 1 && type == Move.FREE) {
                            enPassantMoves.add(board.getCoordinates(x + 1, y + 1));
                        }
                    }
                }
                if (x - 1 >= 1) {
                    Piece p = board.getPiecebylocation(new Coordinate(x - 1, y));
                    int type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
                    if (p != null) {
                        if (p.color != this.color && Blacks.getInstance().lastMoved == p && p.moves == 1 && type == Move.FREE) {
                            enPassantMoves.add(board.getCoordinates(x - 1, y + 1));
                        }
                    }
                }
            }
        } else {
            if (y - 2 <= 8 && moves == 0) {
                int type1 = board.isEmpty(board.getCoordinates(x, y - 2), color);
                int type2 = board.isEmpty(board.getCoordinates(x, y - 1), color);
                if (type1 == Move.FREE && type2 == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y - 2));
                }
            }
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

            if (y == 4) {
                if (x + 1 <= 8) {
                    Piece p = board.getPiecebylocation(new Coordinate(x + 1, y));
                    int type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
                    if (p != null)
                        if (p.color != this.color && Whites.getInstance().lastMoved == p && p.moves == 1 && type == Move.FREE) {
                            enPassantMoves.add(board.getCoordinates(x + 1, y - 1));
                        }
                }

                if (x - 1 >= 1) {
                    Piece p = board.getPiecebylocation(new Coordinate(x - 1, y));
                    int type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
                    if (p != null) {
                        if (p.color != this.color && Whites.getInstance().lastMoved == p && p.moves == 1 && type == Move.FREE) {
                            enPassantMoves.add(board.getCoordinates(x - 1, y - 1));
                        }
                    }
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

    public void pawnToKnight() {
        //Se seteaza pe pozitia pionului o noua regina
        Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()] = new Knight(coordinate, color);
        //In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            Whites.getInstance().removeWhitePiece(this);
            Whites.getInstance().addWhitePiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            Blacks.getInstance().removeBlackPiece(this);
            Blacks.getInstance().addBlackPiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
    }

    public void pawnToBishop() {
        //Se seteaza pe pozitia pionului o noua regina
        Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()] = new Bishop(coordinate, color);
        //In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            Whites.getInstance().removeWhitePiece(this);
            Whites.getInstance().addWhitePiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            Blacks.getInstance().removeBlackPiece(this);
            Blacks.getInstance().addBlackPiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
    }

    public void pawnToRook() {
        //Se seteaza pe pozitia pionului o noua regina
        Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()] = new Rook(coordinate, color);
        //In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            Whites.getInstance().removeWhitePiece(this);
            Whites.getInstance().addWhitePiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            Blacks.getInstance().removeBlackPiece(this);
            Blacks.getInstance().addBlackPiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
    }

    void pawnPromotion(char toWhat) {

        switch (toWhat) {
            case 'q' -> {
                pawnToQueen();
            }
            case 'n' -> {
                pawnToKnight();
            }
            case 'b' -> {
                pawnToBishop();
            }
            case 'r' -> {
                pawnToRook();
            }
        }
    }

    char promotionGeneration() {
        Random rand = new Random();
        int randomNum = rand.nextInt((4)) + 1;

        switch (randomNum) {
            case 2 -> {
                return 'n';
            }
            case 3 -> {
                return 'b';
            }
            case 4 -> {
                return 'r';
            }
            default -> {
                return 'q';
            }
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
