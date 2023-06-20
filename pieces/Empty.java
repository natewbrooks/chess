package chess.pieces;

import java.util.ArrayList;

import chess.Coordinates;

public class Empty extends Piece {
    public Empty(char row, int col) {
        super(row, col);
    }

    public Empty(int row, int col) {
        super(row, col);
    }

    public Empty(Coordinates coords) {
        super(coords);
    }

    public ArrayList<Coordinates> move() {
        return null;
    }

    public ArrayList<Coordinates> attack() {
        return null;
    }
}
