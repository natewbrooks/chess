package chess.pieces;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import chess.Client;
import chess.Coordinates;
import chess.components.ChessBox;

public class King extends Piece {

    public King(Coordinates c, Team team) {
        super(c, team);
        whiteIcon = new ImageIcon(
                new ImageIcon("chess/icons/WhiteKing60.png").getImage().getScaledInstance(75, 75,
                        Image.SCALE_SMOOTH));
        blackIcon = new ImageIcon(
                new ImageIcon("chess/icons/BlackKing60.png").getImage().getScaledInstance(75, 75,
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

    public King(char row, int col) {
        super(row, col);
        whiteIcon = new ImageIcon("chess/icons/WhiteKing60.png");
        blackIcon = new ImageIcon("chess/icons/BlackKing60.png");
    }

    public ArrayList<Coordinates> move() {
        ArrayList<Coordinates> endpoints = new ArrayList<>();
        int[][] offsets = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        for (int[] offset : offsets) {
            Coordinates target = new Coordinates(coords.row + offset[0], coords.col + offset[1]);
            ChessBox box = Client.board.search(target.position);
            if (box != null) {
                Piece piece = box.chessPiece;
                if (piece instanceof Empty || piece.team != this.team) {
                    endpoints.add(target);
                }
            }
        }
        return endpoints;
    }

    public ArrayList<Coordinates> attack() {
        // In Chess, the King's attack is the same as its move
        return move();
    }
}
