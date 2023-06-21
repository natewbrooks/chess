package chess.pieces;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import chess.Client;
import chess.Coordinates;
import chess.components.ChessBox;

public abstract class Piece extends JLabel {
    public Coordinates coords;
    public Team team = Team.NONE;
    public ImageIcon whiteIcon;
    public ImageIcon blackIcon;
    public int worth = 1;
    public boolean first_move = true;

    public Piece(Coordinates c, Team team) {
        coords = c;
        this.team = team;
        this.setPreferredSize(new Dimension(120, 120));
    }

    public Piece(char row, int col) {
        coords = new Coordinates(row, col);
    }

    public Piece(int row, int col) {
        coords = new Coordinates(row, col);
    }

    public enum Team {
        WHITE,
        BLACK,
        NONE
    }

    public abstract ArrayList<Coordinates> move();

    public abstract ArrayList<Coordinates> attack();

    protected boolean isOnBoard(Coordinates target) {
        int row = target.row;
        int col = target.col;
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    protected boolean isEmpty(Coordinates target) {
        ChessBox box = Client.board.search(target.position);
        return box != null && box.chessPiece instanceof Empty;
    }

    protected boolean hasOpponent(Coordinates target) {
        ChessBox box = Client.board.search(target.position);
        return box != null && box.chessPiece.team != this.team && box.chessPiece.team != Team.NONE;
    }

    public String coordinates() {
        return coords.position;
    }
}