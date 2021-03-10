package com.chess;

public class Brain {
    private static Brain instance = null;
    Piece piece;
    int color;

    private Brain() {
        piece = Board.getInstance().getPiecebylocation(Board.getInstance().getCoordinates(1,7));
        color = Game.getInstance().enginecolor;
    }

    public static Brain getInstance() {
        if (instance == null)
            instance = new Brain();
        return instance;
    }

    public void doPawnMove(){
        if (color == TeamColor.BLACK) {
            piece.generateMoves();
            if (piece.captureMoves != null) {
                if (!piece.captureMoves.isEmpty()) {
                    Coordinate c = piece.captureMoves.get(0);
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY());
                    System.out.flush();

                    //piece.movePiece(c);
                    Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY());
                    return;
                }
            }
            if (piece.freeMoves != null) {
                if (!piece.freeMoves.isEmpty()) {
                    Coordinate c = piece.freeMoves.get(0);
                    System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY());
                    System.out.flush();

                    Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                            c.getCharX() + c.getY());
                    return;
                }
            }
            //System.out.println(piece.toString() + " " + piece.freeMoves + " " + piece.captureMoves);
            System.out.println("resign");
            System.out.flush();
        }
    }
}
