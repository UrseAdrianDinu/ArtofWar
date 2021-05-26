package com.chess;

import java.util.ArrayList;

/*
    Clasa pentru reprezentarea tablei de joc
    Parametrii clasei:
        coordinates: matrice pentru a retine coordonatele tablei
        table: tabla de joc
 */
public class Board {

    Coordinate[][] coordinates;
    Piece[][] table;

    int numberofwhitepieces = 0;
    int numberofwhitepawns = 0;
    ArrayList<Piece> whites;
    Piece WhitelastMoved;

    int numberofblackpieces = 0;
    int numberofblackpawns = 0;
    ArrayList<Piece> blacks;
    Piece BlacklastMoved;
    int engineChessNumber = 0;
    int enemyChessNumber = 0;


    // Singleton Pattern
    public Board() {
        table = new Piece[9][9];
        whites = new ArrayList<>();
        blacks = new ArrayList<>();
    }


    Board copie() throws CloneNotSupportedException {
        Board b = new Board();
        b.whites = new ArrayList<>();
        b.blacks = new ArrayList<>();

        b.table = new Piece[9][9];
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (table[i][j] != null) {
                    b.table[i][j] = createPiece(table[i][j]);
                    if (b.table[i][j].color == TeamColor.WHITE) {
                        b.whites.add(b.table[i][j]);
                    } else {
                        b.blacks.add(b.table[i][j]);
                    }
                } else {
                    b.table[i][j] = null;
                }
            }
        }

        b.numberofwhitepawns = numberofwhitepawns;
        b.numberofwhitepieces = numberofwhitepieces;
        b.numberofblackpawns = numberofblackpawns;
        b.numberofblackpieces = numberofblackpieces;
        b.enemyChessNumber = enemyChessNumber;
        b.engineChessNumber = engineChessNumber;

        return b;
    }

    Piece createPiece(Piece p) {
        String s = p.getType();

        switch (s) {
            case "King":
                return new King((King) p);

            case "Pawn":
                return new Pawn((Pawn) p);

            case "Queen":
                return new Queen((Queen) p);

            case "Rook":
                return new Rook((Rook) p);

            case "Bishop":
                return new Bishop((Bishop) p);

            case "Knight":
                return new Knight((Knight) p);

            default:
                return null;
        }
    }
    // Metoda care reinitializeaza tabla de joc


    // Metoda care intoarce o noua coordonata din x si y
    // Am facut aceasta metoda pentru a reduce numarul multiplu
    // de instatieri ale unei coordonate
    public Coordinate getCoordinates(int x, int y) {
        if (coordinates == null) {
            coordinates = new Coordinate[9][9];
        }
        if (coordinates[x][y] != null) {
            return coordinates[x][y];
        }
        coordinates[x][y] = new Coordinate(x, y);

        return coordinates[x][y];
    }

    // Metoda care intoarce ce tip de mutare se poate face
    // la coordonata coordinate in functie de culoare
    public int isEmpty(Coordinate coordinate, int color) {
        if (getPiecebylocation(coordinate) == null) {
            return Move.FREE;
        }
        if (getPiecebylocation(coordinate).color == color) {
            return Move.BLOCK;
        }
        return Move.CAPTURE;
    }

    // Metoda care initializeaza tabla de joc
    void initBoard() {
        int i;

        // Punerea nebunilor pe tabla
        table[1][3] = new Bishop(new Coordinate(3, 8), TeamColor.BLACK);
        table[1][6] = new Bishop(new Coordinate(6, 8), TeamColor.BLACK);
        table[8][3] = new Bishop(new Coordinate(3, 1), TeamColor.WHITE);
        table[8][6] = new Bishop(new Coordinate(6, 1), TeamColor.WHITE);
        blacks.add(table[1][3]);
        blacks.add(table[1][6]);
        whites.add(table[8][3]);
        whites.add(table[8][6]);

        // Punerea reginelor pe tabla
        table[1][4] = new Queen(new Coordinate(4, 8), TeamColor.BLACK);
        table[8][4] = new Queen(new Coordinate(4, 1), TeamColor.WHITE);
        blacks.add(table[1][4]);
        whites.add(table[8][4]);


        // Punerea cailor pe tabla
        table[1][2] = new Knight(new Coordinate(2, 8), TeamColor.BLACK);
        table[1][7] = new Knight(new Coordinate(7, 8), TeamColor.BLACK);
        table[8][2] = new Knight(new Coordinate(2, 1), TeamColor.WHITE);
        table[8][7] = new Knight(new Coordinate(7, 1), TeamColor.WHITE);
        blacks.add(table[1][2]);
        blacks.add(table[1][7]);
        whites.add(table[8][2]);
        whites.add(table[8][7]);


        // Punerea turelor pe tabla
        table[1][1] = new Rook(new Coordinate(1, 8), TeamColor.BLACK);
        table[1][8] = new Rook(new Coordinate(8, 8), TeamColor.BLACK);
        table[8][1] = new Rook(new Coordinate(1, 1), TeamColor.WHITE);
        table[8][8] = new Rook(new Coordinate(8, 1), TeamColor.WHITE);
        blacks.add(table[1][1]);
        blacks.add(table[1][8]);
        whites.add(table[8][1]);
        whites.add(table[8][8]);


        for (i = 1; i <= 8; i++) {
            // Punerea pionilor pe tabla
            table[2][i] = new Pawn(new Coordinate(i, 7), TeamColor.BLACK);
            table[7][i] = new Pawn(new Coordinate(i, 2), TeamColor.WHITE);
            // Adaugarea lor in clasele Black/Whites
            blacks.add(table[2][i]);
            whites.add(table[7][i]);
        }

        // Punerea regilor pe tabla
        table[1][5] = new King(new Coordinate(5, 8), TeamColor.BLACK);
        table[8][5] = new King(new Coordinate(5, 1), TeamColor.WHITE);
        //Brain.getInstance().blackKing = table[1][5];
        //Brain.getInstance().whiteKing = table[8][5];
        blacks.add(table[1][5]);
        whites.add(table[8][5]);
        numberofwhitepieces = 16;
        numberofwhitepawns = 8;
        numberofblackpieces = 16;
        numberofblackpawns = 8;

    }

    // Metoda care intoarce piesa de la coordonata coordinate
    // realizand legatura dintre coordonta de pe tabla de joc
    // si pozitia din matrice
    public Piece getPiecebylocation(Coordinate coordinate) {
        return table[9 - coordinate.getY()][coordinate.getIntX()];
    }

    // Metoda care executa o mutare pe baza unui string
    // trimis de xboard (Ex. a2a3)
    public void executeMove(String s) {
        //System.out.println("EXECUTE MOVE " + s);
        // Identificarea coordonatelor de inceput si final
        char xi = s.charAt(0);
        int yi = s.charAt(1) - 48;
        char xf = s.charAt(2);
        int yf = s.charAt(3) - 48;
        //System.out.println("AHAHAH: " + s);
        Coordinate c = getCoordinates(xf - 96, yf);
        Piece p = getPiecebylocation(getCoordinates(xi - 96, yi));
        //System.out.println("CULOARE: " + s + p + (xi - 96) + yi);
        //System.out.println(this);
        if (p.color == TeamColor.WHITE) {
            WhitelastMoved = p;
        } else {
            BlacklastMoved = p;
        }

        if (p.getType().compareTo("King") == 0) {
            if (p.color == TeamColor.BLACK) {
                if (s.compareTo("e8g8") == 0) {
//                    System.out.println("REGE: " + p.freeMoves);
                    p.movePiece(c, this);
                    Piece rook = getPiecebylocation(getCoordinates(8, 8));
                    Coordinate cRook = getCoordinates(6, 8);
                    rook.movePiece(cRook, this);
                    return;
                }
                if (s.compareTo("e8c8") == 0) {
//                    System.out.println("REGE: " + p.freeMoves);
//                    System.out.println("TABLAREGE" + this);
                    p.movePiece(c, this);
                    Piece rook = getPiecebylocation(getCoordinates(1, 8));
                    Coordinate cRook = getCoordinates(4, 8);
                    rook.movePiece(cRook, this);
                    return;
                }
            } else {
                if (s.compareTo("e1g1") == 0) {
//                    System.out.println("REGE: " + p.freeMoves);
//                    System.out.println("TABLAREGE" + this);
//                    System.out.println("BEFORE");
//                    System.out.println(this);
                    p.movePiece(c, this);
//                    System.out.println("AFTER");
                    Piece rook = getPiecebylocation(getCoordinates(8, 1));
                    Coordinate cRook = getCoordinates(6, 1);
                    rook.movePiece(cRook, this);
                    return;
                }
                if (s.compareTo("e1c1") == 0) {
//                    System.out.println("REGE: " + p.freeMoves);
//                    System.out.println("TABLAREGE" + this);
                    p.movePiece(c, this);
                    Piece rook = getPiecebylocation(getCoordinates(1, 1));
                    Coordinate cRook = getCoordinates(4, 1);
                    rook.movePiece(cRook, this);
                    return;
                }
            }
        }

        // Cazul in care se executa o miscare en-passant
        if (p.getType().compareTo("Pawn") == 0) {
            Pawn pion = (Pawn) p;
            pion.generateMoves(this);
            if (pion.enPassantMoves.contains(c)) {
                if (pion.color == TeamColor.BLACK) {
                    p.movePiece(c, this);
                    whites.remove(getPiecebylocation(new Coordinate(c.getIntX(), c.getY() + 1)));
                    numberofwhitepieces--;
                    numberofwhitepawns--;
                    table[9 - c.getY() - 1][c.getIntX()] = null;
                    return;
                } else {
                    p.movePiece(c, this);
                    blacks.remove(getPiecebylocation(new Coordinate(c.getIntX(), c.getY() - 1)));
                    numberofblackpieces--;
                    numberofblackpawns--;
                    table[9 - c.getY() + 1][c.getIntX()] = null;
                    return;
                }
            }

            if (p.color == Game.getInstance().enginecolor) {
                if (p.color == TeamColor.BLACK && c.getY() == 1) {
                    //System.out.println("PROMOTION");
                    p.movePiece(c, this);
                    ((Pawn) p).pawnToQueen(this);
                    return;
                } else {
                    if (p.color == TeamColor.WHITE && c.getY() == 8) {
                        p.movePiece(c, this);
                        ((Pawn) p).pawnToQueen(this);
                        return;
                    }
                }
            }
        }


        p.movePiece(c, this);
//        System.out.println("Am schimbat");
//        System.out.println(p.coordinate);

        // Cazul in care se executa o promovare a pionilor
        if (s.length() > 4) {
            char promote = s.charAt(4);
            if (p.color == TeamColor.WHITE) {
                ((Pawn) p).pawnPromotion(promote, this);
            }
            if (p.color == TeamColor.BLACK) {
                ((Pawn) p).pawnPromotion(promote, this);
            }
        }

    }

    Piece[][] createCopy() {
        Piece[][] copy = new Piece[9][9];
        for (int i = 1; i <= 8; i++) {
            System.arraycopy(table[i], 1, copy[i], 1, 8);
        }
        return copy;
    }

    public Piece getBlackPiece() {
        for (Piece piece : blacks) {
            if (!piece.getType().equals("Pawn")) {
                if (piece.freeMoves.size() != 0 || piece.captureMoves.size() != 0) {
                    return piece;
                }
            } else {
                Pawn pion = (Pawn) piece;
                if (pion.freeMoves.size() != 0 || pion.captureMoves.size() != 0 || pion.enPassantMoves.size() != 0) {
                    return piece;
                }
            }
        }
        return null;
    }

    // Metoda care intoarce coordonata regelui
    public Coordinate getBlackKingLocation() {
        for (Piece piece : blacks) {
            if (piece.getType().compareTo("King") == 0) {
                return piece.coordinate;
            }
        }
        return null;
    }

    public Coordinate getWhiteKingLocation() {
        for (Piece piece : whites) {
            if (piece.getType().compareTo("King") == 0) {
                return piece.coordinate;
            }
        }
        return null;
    }

    public Piece getWhiteKing() {
        for (Piece piece : whites) {
            if (piece.getType().compareTo("King") == 0) {
                return piece;
            }
        }
        return null;
    }

    public Piece getBlackKing() {
        for (Piece piece : blacks) {
            if (piece.getType().compareTo("King") == 0) {
                return piece;
            }
        }
        return null;
    }


    // Metoda care intoarce o piesa alba care
    // are mutari posibile

    public Piece getWhitePiece() {
        for (Piece piece : whites) {
            //piece.generateMoves();
            if (!piece.getType().equals("Pawn")) {
                if (piece.freeMoves.size() != 0 || piece.captureMoves.size() != 0) {
                    return piece;
                }
            } else {
                Pawn pion = (Pawn) piece;
                if (pion.freeMoves.size() != 0 || pion.captureMoves.size() != 0 || pion.enPassantMoves.size() != 0) {
                    return piece;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "";
        int i, j;
        for (i = 1; i <= 8; i++) {
            for (j = 1; j <= 8; j++) {
                if (table[i][j] == null)
                    s += " null ";
                else
                    s += table[i][j];
            }
            s += "\n";
        }
        return s;
    }

    int kingSafety(int color) {
        int scor = 0;
        if (color == TeamColor.BLACK) {
            King ourking = (King) getBlackKing();
            if (ourking == null) {
                return 0;
            }

            //System.out.println(ourking.coordinate + " " + ourking.color);
            Piece p1 = null, p2 = null, p3 = null;
            if (ourking.coordinate.getY() - 1 >= 1) {
                if (ourking.coordinate.getIntX() + 1 <= 8)
                    p1 = getPiecebylocation(new Coordinate(ourking.coordinate.getIntX() + 1, ourking.coordinate.getY() - 1));
                if (ourking.coordinate.getIntX() - 1 >= 1)
                    p2 = getPiecebylocation(new Coordinate(ourking.coordinate.getIntX() - 1, ourking.coordinate.getY() - 1));

                p3 = getPiecebylocation(new Coordinate(ourking.coordinate.getIntX(), ourking.coordinate.getY() - 1));
            }
            if (p1 != null) {
                scor += 2;
            }
            if (p2 != null) {
                scor += 2;
            }
            if (p3 != null) {
                scor += 1;
            }
        } else {
            King ourking = (King) getWhiteKing();
            if (ourking == null) {
                return 0;
            }
            //System.out.println(ourking.coordinate + " " + ourking.color);
            Piece p1 = null, p2 = null, p3 = null;
            if (ourking.coordinate.getY() + 1 <= 8) {
                if (ourking.coordinate.getIntX() + 1 <= 8)
                    p1 = getPiecebylocation(new Coordinate(ourking.coordinate.getIntX() + 1, ourking.coordinate.getY() + 1));
                if (ourking.coordinate.getIntX() - 1 >= 1)
                    p2 = getPiecebylocation(new Coordinate(ourking.coordinate.getIntX() - 1, ourking.coordinate.getY() + 1));
                p3 = getPiecebylocation(new Coordinate(ourking.coordinate.getIntX(), ourking.coordinate.getY() + 1));
            }
            if (p1 != null) {
                scor += 2;
            }
            if (p2 != null) {
                scor += 2;
            }
            if (p3 != null) {
                scor += 2;
            }
        }
        return scor;
    }

    int evaluateBoard(boolean MaximizingPlayer) {
        int s1 = 0;
        int s2 = 0;

        // SAH EVALUARE
        for (Piece p : whites) {
            s1 += Scores.getScore(p.getTypeint(), p.coordinate.getIntX(), p.coordinate.getY(), p.color, numberofblackpieces + numberofwhitepieces);
            s1 += p.support * 2;
            if (p.getType().equals("Pawn") && p.support == 0) {
                s1 -= 2;
            }
            if (p.getType().equals("Pawn") && p.captureMoves.size() == 0 && p.freeMoves.size() == 0) {
                s1 -= 2;
            }
        }

        for (Piece p : blacks) {
            s2 += Scores.getScore(p.getTypeint(), p.coordinate.getIntX(), p.coordinate.getY(), p.color, numberofblackpieces + numberofwhitepieces);
            if (p.getType().equals("Pawn") && p.support == 0) {
                s2 -= 2;
            }
            if (p.getType().equals("Pawn") && p.captureMoves.size() == 0 && p.freeMoves.size() == 0) {
                s2 -= 2;
            }

            s2 += p.support * 2;
        }

        if (Brain.getInstance().checkChessEvaluation(this, TeamColor.WHITE).size() != 0) {
            if (Game.getInstance().enginecolor == TeamColor.BLACK) {
                //s1 -= 100;
                engineChessNumber++;
            } else {
                //s2 -= 100;
                enemyChessNumber++;
            }
            //System.out.println("mama");
        }

        if (Brain.getInstance().checkChessEvaluation(this, TeamColor.BLACK).size() != 0) {
            if (Game.getInstance().enginecolor == TeamColor.WHITE) {
                //s1 += 200;
                engineChessNumber++;
            } else {
                //s1 += 100;
                enemyChessNumber++;
            }
            //System.out.println("tata");
        }
        int engine = Brain.getInstance().enginesquares;
        int adv = Brain.getInstance().enemysquares;
        int k1 = kingSafety(TeamColor.BLACK);
        int k2 = kingSafety(TeamColor.WHITE);
        if (Game.getInstance().enginecolor == TeamColor.BLACK) {
            return s2 - s1 + 200 * (engineChessNumber - enemyChessNumber) + 3 * (engine - adv);
        } else {
            return s1 - s2 + 200 * (engineChessNumber - enemyChessNumber) + 3 * (engine - adv);
        }
    }
}
