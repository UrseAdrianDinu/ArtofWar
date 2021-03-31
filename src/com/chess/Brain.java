package com.chess;

/*
    Clasa care implementeaza gandirea engine-ului.
    Pentru moment, ea decide mutarile pionilor.
 */
public class Brain {

    //Singleton Pattern
    private static Brain instance = null;


    private Brain() {

    }

    public static Brain getInstance() {
        if (instance == null)
            instance = new Brain();
        return instance;
    }


    //Metoda care trimite xboard-ului o mutare a unui pion
    //in functie de culoarea piesei.
    //In cazul in care nu mai este nicio mutare posibila
    //vom da resign.
    public void doPawnMove() {
        Game game = Game.getInstance();
        Piece piece;
        //Se selecteaza un pion in functie de culoarea engine-ului
        if (game.enginecolor == TeamColor.BLACK) {
            piece = Blacks.getInstance().getPawn();
        } else {
            piece = Whites.getInstance().getPawn();
        }

        //Daca nu mai exista pioni cu mutari disponibile
        //atunci dam resign
        if (piece == null) {
            System.out.println("resign");
            System.out.flush();
            return;
        }

        //Se genereaza mutarile posibile
        piece.generateMoves();
        //Am prioritizat mutarile de capturare
        if (piece.captureMoves.size() != 0) {
            Coordinate c = piece.captureMoves.get(0);
            //Se scrie la standard output comanda "move + mutarea gasita"
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();
            //Se updateaza tabla de joc
            Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }
        //Mutari libere
        if (piece.freeMoves.size() != 0) {
            Coordinate c = piece.freeMoves.get(0);
            //Se scrie la standard output comanda "move + mutarea gasita"
            System.out.println("move " + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            System.out.flush();
            //Se updateaza tabla de joc
            Board.getInstance().executeMove("" + piece.coordinate.getCharX() + piece.coordinate.getY() +
                    c.getCharX() + c.getY());
            return;
        }

        System.out.println("resign");
        System.out.flush();
    }
}
