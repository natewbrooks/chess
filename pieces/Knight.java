package chess.pieces;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import chess.Client;
import chess.Coordinates;
import chess.components.ChessBox;

public class Knight extends Piece {

    public Knight(Coordinates c, Team team) {
        super(c, team);
        whiteIcon = new ImageIcon(
                new ImageIcon("chess/icons/WhiteKnight60.png").getImage().getScaledInstance(75, 75,
                        Image.SCALE_SMOOTH));
        blackIcon = new ImageIcon(
                new ImageIcon("chess/icons/BlackKnight60.png").getImage().getScaledInstance(75, 75,
                        Image.SCALE_SMOOTH));
        switch (team) {
            case WHITE:
                this.setIcon(whiteIcon);
                break;
            case BLACK:
                this.setIcon(blackIcon);
                break;
            case NONE:
                this.setIcon(null);
                break;
        }
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public Knight(char row, int col) {
        super(row, col);
        whiteIcon = new ImageIcon("chess/icons/WhiteKnight60.png");
        blackIcon = new ImageIcon("chess/icons/BlackKnight60.png");
    }

    public ArrayList<Coordinates> move() {
        ArrayList<Coordinates> endpoints = new ArrayList<>();
        int[][] offsets = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 }, { 2, 1 } };
        for (int[] offset : offsets) {
            Coordinates target = new Coordinates(coords.row + offset[0], coords.col + offset[1]);
            if (isOnBoard(target) && isEmpty(target)) {
                endpoints.add(target);
            }
        }
        return endpoints;
    }

    public ArrayList<Coordinates> attack() {
        ArrayList<Coordinates> endpoints = new ArrayList<>();
        int[][] offsets = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 }, { 2, 1 } };
        for (int[] offset : offsets) {
            Coordinates target = new Coordinates(coords.row + offset[0], coords.col + offset[1]);
            if (isOnBoard(target) && hasOpponent(target)) {
                endpoints.add(target);
            }
        }
        return endpoints;
    }
}
