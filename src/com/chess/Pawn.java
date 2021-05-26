package com.chess;

import java.util.ArrayList;
import java.util.Random;

/*
    Clasa specifica piesei "Pion"
    Parametrii clasei:
        enPassantMoves: vector pentru mutarile en-passant posibile
 */

public class Pawn extends Piece {

    ArrayList<Coordinate> enPassantMoves;

    // Initializare coordonata si culoare
    public Pawn(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
        this.value = 10;
    }

    public Pawn(int x, int y, int color) {
        coordinate = new Coordinate(x, y);
        this.color = color;
        this.value = 10;
    }

    public Pawn(Coordinate coordinate, int color, ArrayList<Coordinate> freemoves,
                ArrayList<Coordinate> capturemoves, ArrayList<Coordinate> enpassantmoves, int value, int moves, int support, int turns) {

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
        this.enPassantMoves = new ArrayList<>();
        for (Coordinate c : enpassantmoves) {
            this.enPassantMoves.add(new Coordinate(c.getIntX(), c.getY()));
        }

    }

    public Pawn(Pawn pawn) {
        this(pawn.coordinate, pawn.color, pawn.freeMoves, pawn.captureMoves, pawn.enPassantMoves, pawn.value, pawn.moves, pawn.support, pawn.turns);
    }

    // Metoda care genereaza mutarile posible pentru pion
    // La fiecare apel vectorii freeMoves/captureMoves/enPassantMoves
    // se reinitializeaza
    public void generateMoves(Board board) {

        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        enPassantMoves = new ArrayList<>();
        int x = coordinate.getIntX();
        int y = coordinate.getY();


        // Updatam runda piesei
        if (turns != Game.getInstance().gameturns) {
            turns = Game.getInstance().gameturns;
            support = 0;
        }

        // In functie de culoarea piesei, analizam pozitiile posibile
        // Pionii albi se pot muta vertical in sus
        // Pionii negri se pot muta vertical in jos

        if (color == TeamColor.WHITE) {

            // Verificam daca pionul se poate muta pe a doua casuta din fata lui
            if (y + 2 <= 8 && moves == 0) {
                int type1 = board.isEmpty(board.getCoordinates(x, y + 2), color);
                int type2 = board.isEmpty(board.getCoordinates(x, y + 1), color);
                if (type1 == Move.FREE && type2 == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y + 2));
                }
            }

            // Verificam daca pionul se poate muta pe casuta din fata lui
            if (y + 1 <= 8) {
                int type = board.isEmpty(board.getCoordinates(x, y + 1), color);

                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y + 1));
                }
            }

            // Verificam daca pionul poate captura piesa din diagonala-dreapta
            if (x + 1 <= 8 && y + 1 <= 8) {
                int typecapture = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
                if (typecapture == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x + 1, y + 1));
                }
                if (typecapture == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y + 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }

            // Verificam daca pionul poate captura piesa din diagonala-stanga
            if (x - 1 >= 0 && y + 1 <= 8) {
                int type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x - 1, y + 1));
                }
                if (type == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y + 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }

            // Verificam daca pionul poate face o mutare en-passant
            if (y == 5) {
                if (x + 1 <= 8) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y));
                    int type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
                    if (p != null) {
                        if (p.color != this.color && board.BlacklastMoved == p && p.moves == 1 && type == Move.FREE) {
                            enPassantMoves.add(board.getCoordinates(x + 1, y + 1));
                        }
                    }
                }
                if (x - 1 >= 1) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y));
                    int type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
                    if (p != null) {
                        if (p.color != this.color && board.BlacklastMoved == p && p.moves == 1 && type == Move.FREE) {
                            enPassantMoves.add(board.getCoordinates(x - 1, y + 1));
                        }
                    }
                }
            }
        } else {

            // Verificam daca pionul se poate muta pe a doua casuta din fata lui
            if (y - 2 <= 8 && moves == 0) {
                int type1 = board.isEmpty(board.getCoordinates(x, y - 2), color);
                int type2 = board.isEmpty(board.getCoordinates(x, y - 1), color);
                if (type1 == Move.FREE && type2 == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y - 2));
                }
            }

            // Verificam daca pionul se poate muta pe casuta din fata lui
            if (y - 1 >= 1) {
                int type = board.isEmpty(board.getCoordinates(x, y - 1), color);
                if (type == Move.FREE) {
                    freeMoves.add(board.getCoordinates(x, y - 1));
                }
            }

            // Verificam daca pionul poate captura piesa din diagonala-stanga
            if (x + 1 <= 8 && y - 1 >= 1) {
                int type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x + 1, y - 1));
                }
                if (type == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y - 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }

            // Verificam daca pionul poate captura piesa din diagonala-dreapta
            if (x - 1 >= 0 && y - 1 >= 1) {
                int type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
                if (type == Move.CAPTURE) {
                    captureMoves.add(board.getCoordinates(x - 1, y - 1));
                }
                if (type == Move.BLOCK) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y - 1));
                    if (p.turns != Game.getInstance().gameturns) {
                        p.turns = Game.getInstance().gameturns;
                        p.support = 0;
                    }
                    p.support++;
                }
            }

            // Verificam daca pionul poate face o mutare en-passant
            if (y == 4) {
                if (x + 1 <= 8) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x + 1, y));
                    int type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
                    if (p != null)
                        if (p.color != this.color && board.WhitelastMoved == p && p.moves == 1 && type == Move.FREE) {
                            //System.out.println("ENPASSANT");
                            //System.out.println(p);
                            enPassantMoves.add(board.getCoordinates(x + 1, y - 1));
                        }
                }

                if (x - 1 >= 1) {
                    Piece p = board.getPiecebylocation(board.getCoordinates(x - 1, y));
                    int type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
                    if (p != null) {
                        if (p.color != this.color && board.WhitelastMoved == p && p.moves == 1 && type == Move.FREE) {
                            enPassantMoves.add(board.getCoordinates(x - 1, y - 1));
                        }
                    }
                }

            }
        }
    }


    // Metoda prin care un pion se transforma in Regina
    public void pawnToQueen(Board board) {
        // Se seteaza pe pozitia pionului o noua regina
        board.table[9 - coordinate.getY()][coordinate.getIntX()] = new Queen(coordinate, color);
        // In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            board.whites.remove(this);
            board.whites.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            board.blacks.remove(this);
            board.blacks.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
        System.out.println("REGINAAAAAAAAAAA");
        System.out.println(board);
    }

    public void pawnToKnight(Board board) {
        // Se seteaza pe pozitia pionului un nou cal
        board.table[9 - coordinate.getY()][coordinate.getIntX()] = new Knight(coordinate, color);
        // In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            board.whites.add(this);
            board.whites.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            board.blacks.remove(this);
            board.blacks.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
    }

    public void pawnToBishop(Board board) {
        // Se seteaza pe pozitia pionului un nou nebun
        board.table[9 - coordinate.getY()][coordinate.getIntX()] = new Bishop(coordinate, color);
        // In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            board.whites.remove(this);
            board.whites.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            board.blacks.remove(this);
            board.blacks.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
    }

    public void pawnToRook(Board board) {
        // Se seteaza pe pozitia pionului o noua tura
        board.table[9 - coordinate.getY()][coordinate.getIntX()] = new Rook(coordinate, color);
        // In functie de culoarea pionului, se elimina piesa din Whites/Blacks
        if (color == TeamColor.WHITE) {
            board.whites.remove(this);
            board.whites.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            board.blacks.remove(this);
            board.blacks.add(board.table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
    }

    // Metoda ca transforma promoveaza un pion in functie de caracterul dat
    // ca paramtru
    void pawnPromotion(char toWhat, Board board) {
        switch (toWhat) {
            case 'q':
                pawnToQueen(board);
                break;
            case 'n':
                pawnToKnight(board);
                break;
            case 'b':
                pawnToBishop(board);
                break;
            case 'r':
                pawnToRook(board);
                break;
        }
    }

    // Metoda care genereaza random un caracater folosit pentru
    // promovarea pionilor
    char promotionGeneration() {
        Random rand = new Random();
        int randomNum = rand.nextInt((4)) + 1;
        switch (randomNum) {
            case 2:
                return 'n';
            case 3:
                return 'b';
            case 4:
                return 'r';
            default:
                return 'q';
        }
    }

    @Override
    public String getType() {
        return "Pawn";
    }

    public int getTypeint(){
        return Scores.PAWN;
    }

    public String toString() {
        if (color == 0)
            return "WPawn ";
        return "BPawn ";
    }


}
