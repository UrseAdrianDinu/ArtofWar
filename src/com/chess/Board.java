package com.chess;

public class Board {
    private static Board instance;
    Coordinate[][] coordinates;
    Piece[][] table;

    private Board() {
        table = new Piece[9][9];
    }

    public static synchronized Board getInstance() {
        if (instance == null)
            instance = new Board();
        return instance;
    }


    public static synchronized Board newGame() {
        instance = null;
        return getInstance();
    }

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

    public int isEmpty(Coordinate coordinate, int color) {
        if (getPiecebylocation(coordinate) == null) {
            return Move.FREE;
        }
        if (getPiecebylocation(coordinate).color == color) {
            return Move.BLOCK;
        }
        return Move.CAPTURE;
    }

    void initBoard() {
        int i;
        for (i = 1; i <= 8; i++) {
            table[2][i] = new Pawn(new Coordinate(i, 7), TeamColor.BLACK);
            table[7][i] = new Pawn(new Coordinate(i, 2), TeamColor.WHITE);

            Blacks.getInstance().addBlackPiece(table[2][i]);
            Whites.getInstance().addWhitePiece(table[7][i]);
        }

        table[1][5] = new King(new Coordinate(5, 8), TeamColor.BLACK);
        table[8][5] = new King(new Coordinate(5, 1), TeamColor.WHITE);
        Blacks.getInstance().addBlackPiece(table[1][5]);
        Whites.getInstance().addWhitePiece(table[8][5]);

        table[1][4] = new Queen(new Coordinate(4, 8), TeamColor.BLACK);
        table[8][4] = new Queen(new Coordinate(4, 1), TeamColor.WHITE);
        Blacks.getInstance().addBlackPiece(table[1][4]);
        Whites.getInstance().addWhitePiece(table[8][4]);

        table[1][1] = new Rook(new Coordinate(1, 8), TeamColor.BLACK);
        table[1][8] = new Rook(new Coordinate(8, 8), TeamColor.BLACK);
        table[8][1] = new Rook(new Coordinate(1, 1), TeamColor.WHITE);
        table[8][8] = new Rook(new Coordinate(8, 1), TeamColor.WHITE);
        Blacks.getInstance().addBlackPiece(table[1][1]);
        Blacks.getInstance().addBlackPiece(table[1][8]);
        Whites.getInstance().addWhitePiece(table[8][1]);
        Whites.getInstance().addWhitePiece(table[8][8]);

        table[1][3] = new Bishop(new Coordinate(3, 8), TeamColor.BLACK);
        table[1][6] = new Bishop(new Coordinate(6, 8), TeamColor.BLACK);
        table[8][3] = new Bishop(new Coordinate(3, 1), TeamColor.WHITE);
        table[8][6] = new Bishop(new Coordinate(6, 1), TeamColor.WHITE);
        Blacks.getInstance().addBlackPiece(table[1][3]);
        Blacks.getInstance().addBlackPiece(table[1][6]);
        Whites.getInstance().addWhitePiece(table[8][3]);
        Whites.getInstance().addWhitePiece(table[8][6]);

        table[1][2] = new Knight(new Coordinate(2, 8), TeamColor.BLACK);
        table[1][7] = new Knight(new Coordinate(7, 8), TeamColor.BLACK);
        table[8][2] = new Knight(new Coordinate(2, 1), TeamColor.WHITE);
        table[8][7] = new Knight(new Coordinate(7, 1), TeamColor.WHITE);
        Blacks.getInstance().addBlackPiece(table[1][2]);
        Blacks.getInstance().addBlackPiece(table[1][7]);
        Whites.getInstance().addWhitePiece(table[8][2]);
        Whites.getInstance().addWhitePiece(table[8][7]);

    }


    public Piece getPiecebylocation(Coordinate coordinate) {
        return table[9 - coordinate.getY()][coordinate.getIntX()];
    }


    public void executeMove(String s) {
        char xi = s.charAt(0);
        int yi = s.charAt(1) - 48;
        char xf = s.charAt(2);
        int yf = s.charAt(3) - 48;

        Coordinate c = getCoordinates(xf - 96, yf);
        Piece p = getPiecebylocation(getCoordinates(xi - 96, yi));
        System.out.println(c + "----" + p);
        p.movePiece(c);

        if (p.getType().compareTo("Pawn") == 0) {
            if (p.color == TeamColor.WHITE && p.coordinate.getY() == 8) {
                ((Pawn) p).pawnToQueen();
            }
            if (p.color == TeamColor.BLACK && p.coordinate.getY() == 1) {
                ((Pawn) p).pawnToQueen();
            }
        }
        System.out.println(this.toString());
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
}
