package chess.pieces;

import java.util.ArrayList;

import chess.Coordinates;

public abstract class Piece {
    public Coordinates coords;
    public Team team = Team.WHITE;
    public int worth = 1;
    public boolean first_move = true;

    public Piece(Coordinates c) {
        coords = c;
    }

    public Piece(char row, int col) {
        coords = new Coordinates(row, col);
    }

    public Piece(int row, int col) {
        coords = new Coordinates(row, col);
    }

    public enum Team {
        WHITE,
        BLACK
    }

    public abstract ArrayList<Coordinates> move();

    public abstract ArrayList<Coordinates> attack();

    public String coordinates() {
        return coords.position;
    }
}