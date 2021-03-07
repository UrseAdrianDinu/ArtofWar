package com.chess;

public class Coordinate {
    private char x;
    private int y;

    public Coordinate(char x, int y){
        this.setX(x);
        this.setY(y);
    }

    public Coordinate(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void setX(char x) {
        this.x = x;
    }

    public void setX(int x) {
        this.x = (char) (96 + x);
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getIntX() {
        return (int) (x - 96);
    }

    public char getCharX() {
        return x;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
