package com.chess;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        //System.in.read(new byte[System.in.available()]);
        Game.getInstance().readInput();

    }
}
