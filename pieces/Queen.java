package chess.pieces;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import chess.Client;
import chess.Coordinates;
import chess.components.ChessBox;

public class Queen extends Piece {

    public Queen(Coordinates c, Team team) {
        super(c, team);
        whiteIcon = new ImageIcon(
                new ImageIcon("chess/icons/WhiteQueen60.png").getImage().getScaledInstance(75, 75,
                        Image.SCALE_SMOOTH));
        blackIcon = new ImageIcon(
                new ImageIcon("chess/icons/BlackQueen60.png").getImage().getScaledInstance(75, 75,
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

    public Queen(char row, int col) {
        super(row, col);
        whiteIcon = new ImageIcon("chess/icons/WhiteQueen60.png");
        blackIcon = new ImageIcon("chess/icons/BlackQueen60.png");
    }

    public ArrayList<Coordinates> move() {
        ArrayList<Coordinates> endpoints = new ArrayList<>();
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
        for (int[] direction : directions) {
            for (int i = 1; i <= 8; i++) {
                Coordinates target = new Coordinates(coords.row + direction[0] * i, coords.col + direction[1] * i);
                if (!isOnBoard(target))
                    break;
                ChessBox targetSquare = Client.board.search(target.position);
                if (targetSquare == null || !isEmpty(target)) {
                    if (targetSquare != null && hasOpponent(target)) {
                        endpoints.add(target);
                    }
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
