package chess.pieces;

import java.util.ArrayList;

import chess.Coordinates;

public class King extends Piece {
    public King(Coordinates c) {
        super(c);
    }

    public King(char row, int col) {
        super(row, col);
    }

    public ArrayList<Coordinates> move() {
        ArrayList<Coordinates> endpoints = new ArrayList<Coordinates>();
        if (first_move) {
            endpoints.add(new Coordinates(coords.row + 2, coords.col));
        }
        endpoints.add(new Coordinates(coords.row + 1, coords.col));
        return endpoints;
    }

    public ArrayList<Coordinates> attack() {
        ArrayList<Coordinates> endpoints = new ArrayList<Coordinates>();
        endpoints.add(new Coordinates(coords.row + 1, coords.col - 1));
        endpoints.add(new Coordinates(coords.row + 1, coords.col + 1));
        return endpoints;
    }
}
