package com.chess;

public class Brain {
    private static Brain instance = null;
    Piece piece;
    private int color;

    private Brain() {
        color = Game.getInstance().enginecolor;
    }

    public static Brain getInstance() {
        if (instance == null)
            instance = new Brain();
        return instance;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setPiece() {
        if (color == TeamColor.BLACK) {
            piece = Blacks.getInstance().getPawn();
        } else {
            piece = Whites.getInstance().getPawn();
        }
    }

    public void doPawnMove() {
        //piece.generateMoves();
        if (piece.captureMoves.size() != 0) {
            Coordinate c = piece.captureMoves.get(0);
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();

            Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }
        if (piece.freeMoves.size() != 0) {
            Coordinate c = piece.freeMoves.get(0);
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();

            //System.out.println("move a2a3");
            //System.out.flush();

            Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }
        //mama
        System.out.println("resign");
        System.out.flush();
    }
}
