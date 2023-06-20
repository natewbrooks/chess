package chess.pieces;

import java.util.ArrayList;

import chess.Coordinates;

public class Pawn extends Piece {
    public Pawn(Coordinates c) {
        super(c);
    }

    public Pawn(char row, int col) {
        super(row, col);
    }

    public ArrayList<Coordinates> move() {
        ArrayList<Coordinates> endpoints = new ArrayList<Coordinates>();
        if (first_move) {
            endpoints.add(new Coordinates(team == Team.WHITE ? coords.row + 2 : coords.row - 2, coords.col));
        }
        endpoints.add(new Coordinates(team == Team.WHITE ? coords.row + 1 : coords.row - 1, coords.col));
        return endpoints;
    }

    public ArrayList<Coordinates> attack() {
        ArrayList<Coordinates> endpoints = new ArrayList<Coordinates>();
        endpoints.add(new Coordinates(team == Team.WHITE ? coords.row + 1 : coords.row - 1,
                team == Team.WHITE ? coords.col - 1 : coords.col + 1));
        endpoints.add(new Coordinates(team == Team.WHITE ? coords.row + 1 : coords.row - 1,
                team == Team.WHITE ? coords.col + 1 : coords.col - 1));
        return endpoints;
    }
}
