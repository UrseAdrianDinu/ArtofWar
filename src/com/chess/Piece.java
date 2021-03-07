package com.chess;

import java.util.ArrayList;

public abstract class Piece {
    ArrayList<Coordinate> freeMoves;
    ArrayList<Coordinate> captureMoves;
    Coordinate coordinate;
    int color;

    public abstract String getType();

    public abstract void generateMoves();

    public abstract String toString();
}