package com.chess;

public class Pair {
    String c;
    int scor;

    Pair(String c, int scor){
        this.scor = scor;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "c='" + c + '\'' +
                ", scor=" + scor +
                '}';
    }
}
