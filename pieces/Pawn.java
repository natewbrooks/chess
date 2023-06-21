package chess.pieces;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import chess.Client;
import chess.Coordinates;
import chess.components.ChessBox;

public class Pawn extends Piece {

    public boolean enPassantAvailable;
    public ChessBox lastMovedPawn;

    public Pawn(Coordinates c, Team team) {
        super(c, team);
        whiteIcon = new ImageIcon(
                new ImageIcon("chess/icons/WhitePawn60.png").getImage().getScaledInstance(75, 75,
                        Image.SCALE_SMOOTH));
        blackIcon = new ImageIcon(
                new ImageIcon("chess/icons/BlackPawn60.png").getImage().getScaledInstance(75, 75,
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
        enPassantAvailable = false; // Initialize en passant availability to false
        lastMovedPawn = null; // Initialize the last moved pawn reference to null
    }

    public Pawn(char row, int col) {
        super(row, col);
        whiteIcon = new ImageIcon("chess/icons/WhitePawn60.png");
        blackIcon = new ImageIcon("chess/icons/BlackPawn60.png");
    }

    // public ArrayList<Coordinates> move() {
    // ArrayList<Coordinates> endpoints = new ArrayList<Coordinates>();
    // Coordinates target = new Coordinates(team == Team.WHITE ? coords.row + 2 :
    // coords.row - 2, coords.col);
    // if (first_move && Client.board.search(target.position) != null
    // && Client.board.search(target.position).chessPiece instanceof Empty) {
    // endpoints.add(target);
    // }

    // target = new Coordinates(team == Team.WHITE ? coords.row + 1 : coords.row -
    // 1, coords.col);
    // if (Client.board.search(target.position) != null
    // && Client.board.search(target.position).chessPiece instanceof Empty) {
    // endpoints.add(target);
    // }

    // return endpoints;
    // }

    public ArrayList<Coordinates> move() {
        ArrayList<Coordinates> endpoints = new ArrayList<>();
        int rowOffset = (team == Team.WHITE) ? 1 : -1;
        Coordinates target = new Coordinates(coords.row + rowOffset, coords.col);
        ChessBox box = Client.board.search(target.position);
        if (box != null) {
            Piece piece = box.chessPiece;
            if (piece instanceof Empty) {
                endpoints.add(target);
                if (first_move) {
                    target = new Coordinates(coords.row + 2 * rowOffset, coords.col);
                    box = Client.board.search(target.position);
                    if (box != null) {
                        piece = box.chessPiece;
                        if (piece instanceof Empty) {
                            endpoints.add(target);
                        }
                    }
                }
            }
        }
        return endpoints;
    }

    public ArrayList<Coordinates> attack() {
        ArrayList<Coordinates> endpoints = new ArrayList<Coordinates>();
        int rowOffset = (team == Team.WHITE) ? 1 : -1;
        Coordinates target1 = new Coordinates(coords.row + rowOffset, coords.col - rowOffset);
        Coordinates target2 = new Coordinates(coords.row + rowOffset, coords.col + rowOffset);
        ChessBox box1 = Client.board.search(target1.position);
        ChessBox box2 = Client.board.search(target2.position);

        if (box1 != null) {
            Piece piece = box1.chessPiece;
            if (!(piece instanceof Empty)) {
                endpoints.add(target1);
            }
        }

        if (box2 != null) {
            Piece piece = box2.chessPiece;
            if (!(piece instanceof Empty)) {
                endpoints.add(target2);
            }
        }

        Coordinates enPassantTarget = new Coordinates(coords.row, coords.col + rowOffset);
        ChessBox enPassantBox = Client.board.search(enPassantTarget.position);
        if (isOnBoard(enPassantTarget) && enPassantAvailable && enPassantBox == lastMovedPawn) {
            System.out.println("ENPASSANT BB");
            endpoints.add(enPassantTarget);
        }

        return endpoints;
    }

    public void setLastMovedPawn(ChessBox box) {
        lastMovedPawn = box;
    }

    public void updateEnPassantAvailability() {
        enPassantAvailable = lastMovedPawn != null && lastMovedPawn.chessPiece instanceof Pawn
                && lastMovedPawn.chessPiece.team != team
                && Math.abs(lastMovedPawn.chessPiece.coords.row - coords.row) == 2;
    }

}
