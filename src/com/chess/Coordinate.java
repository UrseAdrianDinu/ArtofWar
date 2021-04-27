package com.chess;

import java.util.Objects;

/*
    Clasa pentru reprezentare unei coordonate
 */
public class Coordinate {
    private char x;
    private int y;

    public Coordinate(char x, int y) {
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
        return (x - 96);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
