package com.chess;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Coordinate coordinate, int color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public Pawn(int x, int y, int color) {
        coordinate = new Coordinate(x, y);
        this.color = color;
    }

    public void generateMoves() {
        freeMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
        int x = coordinate.getIntX();
        int y = coordinate.getY();
        Board board = Board.getInstance();
        if (color == TeamColor.WHITE) {
            int type = board.isEmpty(board.getCoordinates(x, y + 1), color);
            if (y + 1 <= 8 && type == Move.FREE)
                freeMoves.add(board.getCoordinates(x, y + 1));
            type = board.isEmpty(board.getCoordinates(x + 1, y + 1), color);
            if (x + 1 <= 8 && y + 1 <= 8 && type == Move.CAPTURE)
                captureMoves.add(board.getCoordinates(x + 1, y + 1));
            type = board.isEmpty(board.getCoordinates(x - 1, y + 1), color);
            if (x - 1 >= 0 && y + 1 <= 8 && type == Move.CAPTURE)
                captureMoves.add(board.getCoordinates(x - 1, y + 1));
        } else {
            int type = board.isEmpty(board.getCoordinates(x, y - 1), color);
            if (y - 1 >= 0 && type == Move.FREE)
                freeMoves.add(board.getCoordinates(x, y - 1));
            type = board.isEmpty(board.getCoordinates(x + 1, y - 1), color);
            if (x + 1 <= 8 && y - 1 >= 0 && type == Move.CAPTURE)
                captureMoves.add(board.getCoordinates(x + 1, y - 1));
            type = board.isEmpty(board.getCoordinates(x - 1, y - 1), color);
            if (x - 1 >= 0 && y - 1 >= 0 && type == Move.CAPTURE)
                captureMoves.add(board.getCoordinates(x - 1, y - 1));
        }
    }

    public void pawnToQueen() {
        Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()] = new Queen(coordinate, color);
        if ( color == TeamColor.WHITE) {
            Whites.getInstance().removeWhitePiece(this);
            Whites.getInstance().addWhitePiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        } else {
            Blacks.getInstance().removeBlackPiece(this);
            Blacks.getInstance().addBlackPiece(Board.getInstance().table[9 - coordinate.getY()][coordinate.getIntX()]);
        }
        System.out.println(Board.getInstance().getPiecebylocation(Board.getInstance().getCoordinates(coordinate.getIntX(), coordinate.getY())).toString());
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
