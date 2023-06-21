package chess.pieces;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import chess.Client;
import chess.Coordinates;
import chess.components.ChessBox;

public class Bishop extends Piece {

    public Bishop(Coordinates c, Team team) {
        super(c, team);
        whiteIcon = new ImageIcon(
                new ImageIcon("chess/icons/WhiteBishop60.png").getImage().getScaledInstance(75, 75,
                        Image.SCALE_SMOOTH));
        blackIcon = new ImageIcon(
                new ImageIcon("chess/icons/BlackBishop60.png").getImage().getScaledInstance(75, 75,
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

    public Bishop(char row, int col) {
        super(row, col);
        whiteIcon = new ImageIcon("chess/icons/WhiteBishop60.png");
        blackIcon = new ImageIcon("chess/icons/BlackBishop60.png");
    }

    public ArrayList<Coordinates> move() {
        ArrayList<Coordinates> endpoints = new ArrayList<>();
        int[][] direction = { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };
        for (int[] d : direction) {
            for (int i = 1; i < 8; i++) {
                int newRow = coords.row + d[0] * i;
                int newCol = coords.col + d[1] * i;
                Coordinates target = new Coordinates(newRow, newCol);
                if (!isOnBoard(target))
                    break;
                if (!isEmpty(target)) {
                    if (hasOpponent(target))
                        endpoints.add(target);
                    break;
                }
                endpoints.add(target);
            }
        }
        return endpoints;
    }

    public ArrayList<Coordinates> attack() {
        return move();
    }
}
