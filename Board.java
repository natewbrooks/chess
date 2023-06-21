package chess;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JLabel;

import chess.components.ChessBox;
import chess.pieces.Bishop;
import chess.pieces.Empty;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Piece.Team;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class Board {
    public Map<String, ChessBox> grid = new LinkedHashMap<String, ChessBox>();
    public int current_round = 1;
    public Team activeTeam = Team.WHITE;
    public final int ROW_SIZE = 8;
    public final int COL_SIZE = 8;

    public void assemble() {
        for (int i = ROW_SIZE; i >= 1; i--) {
            for (char col = 'A'; col <= 'H'; col++) {
                ChessBox box = new ChessBox(i, (int) (col - 'A' + 1));
                grid.put(Character.toString(col) + i, box);
            }
        }
        setupChess();
        // System.out.println(b.keySet());
        // b.get("B2").chessPiece = new Pawn(b.get("B2").chessPiece.coords);
        // b.get("B2").add(new JLabel("PAWN!"));
        return;
    }

    public void setupChess() {
        for (char col = 'A'; col <= 'H'; col++) {
            String s = Character.toString(col);
            switch (col) {
                case 'A':
                case 'H':
                    // Rooks at corners.
                    Rook whiteRook = new Rook(grid.get(s + 1).chessPiece.coords, Team.WHITE);
                    grid.get(s + 1).chessPiece = whiteRook;
                    grid.get(s + 1).updateIcon(whiteRook);

                    Rook blackRook = new Rook(grid.get(s + 8).chessPiece.coords, Team.BLACK);
                    grid.get(s + 8).chessPiece = blackRook;
                    grid.get(s + 8).updateIcon(blackRook);
                    break;
                case 'B':
                case 'G':
                    // Knights.
                    Knight whiteKnight = new Knight(grid.get(s + 1).chessPiece.coords, Team.WHITE);
                    grid.get(s + 1).chessPiece = whiteKnight;
                    grid.get(s + 1).updateIcon(whiteKnight);

                    Knight blackKnight = new Knight(grid.get(s + 8).chessPiece.coords, Team.BLACK);
                    grid.get(s + 8).chessPiece = blackKnight;
                    grid.get(s + 8).updateIcon(blackKnight);
                    break;
                case 'C':
                case 'F':
                    // Bishops.
                    Bishop whiteBishop = new Bishop(grid.get(s + 1).chessPiece.coords, Team.WHITE);
                    grid.get(s + 1).chessPiece = whiteBishop;
                    grid.get(s + 1).updateIcon(whiteBishop);

                    Bishop blackBishop = new Bishop(grid.get(s + 8).chessPiece.coords, Team.BLACK);
                    grid.get(s + 8).chessPiece = blackBishop;
                    grid.get(s + 8).updateIcon(blackBishop);
                    break;
                case 'D':
                    // Queen.
                    Queen whiteQueen = new Queen(grid.get(s + 1).chessPiece.coords, Team.WHITE);
                    grid.get(s + 1).chessPiece = whiteQueen;
                    grid.get(s + 1).updateIcon(whiteQueen);

                    Queen blackQueen = new Queen(grid.get(s + 8).chessPiece.coords, Team.BLACK);
                    grid.get(s + 8).chessPiece = blackQueen;
                    grid.get(s + 8).updateIcon(blackQueen);
                    break;
                case 'E':
                    // King.
                    King whiteKing = new King(grid.get(s + 1).chessPiece.coords, Team.WHITE);
                    grid.get(s + 1).chessPiece = whiteKing;
                    grid.get(s + 1).updateIcon(whiteKing);

                    King blackKing = new King(grid.get(s + 8).chessPiece.coords, Team.BLACK);
                    grid.get(s + 8).chessPiece = blackKing;
                    grid.get(s + 8).updateIcon(blackKing);
                    break;
            }

            // Pawns.
            Pawn whitePawn = new Pawn(grid.get(s + 2).chessPiece.coords, Team.WHITE);
            grid.get(s + 2).chessPiece = whitePawn;
            grid.get(s + 2).updateIcon(whitePawn);

            Pawn blackPawn = new Pawn(grid.get(s + 7).chessPiece.coords, Team.BLACK);
            grid.get(s + 7).chessPiece = blackPawn;
            grid.get(s + 7).updateIcon(blackPawn);
        }
    }

    // public void setupChess() {
    // for (char col = 'A'; col <= 'H'; col++) {
    // String s = Character.toString(col);
    // Pawn white = new Pawn(grid.get(s + 2).chessPiece.coords, Team.WHITE);
    // grid.get(s + 2).chessPiece = white;
    // // add to ui
    // grid.get(s + 2).updateIcon(white);

    // Pawn black = new Pawn(grid.get(s + 7).chessPiece.coords, Team.BLACK);
    // grid.get(s + 7).chessPiece = black;
    // grid.get(s + 7).chessPiece.team = Team.BLACK;
    // // add to ui
    // grid.get(s + 7).updateIcon(black);

    // }
    // }

    public void move(ChessBox currentBox, ChessBox newBox) {
        if (currentBox.chessPiece.team == newBox.chessPiece.team || currentBox.chessPiece.team != activeTeam) {
            // Do not allow a piece to be moved if it's not its team's turn
            return;
        }

        ArrayList<Coordinates> moveLocations = currentBox.chessPiece.move();
        ArrayList<Coordinates> attackLocations = currentBox.chessPiece.attack();

        if (newBox.chessPiece instanceof Empty) {
            if (moveLocations != null && moveLocations.contains(newBox.chessPiece.coords)) {
                newBox.swapChessPieces(currentBox);
            }
        } else if (newBox.chessPiece instanceof Piece) {
            if (attackLocations != null && attackLocations.contains(newBox.chessPiece.coords)) {
                newBox.swapChessPieces(currentBox);
            }
        }

        Piece currentPiece = currentBox.chessPiece;
        if (currentPiece instanceof Pawn) {
            Pawn pawn = (Pawn) currentPiece;
            pawn.setLastMovedPawn(newBox); // Set the last moved pawn reference
            pawn.updateEnPassantAvailability(); // Update en passant availability
        }

        // switch active team
        switch (activeTeam) {
            case WHITE:
                activeTeam = Team.BLACK;
                break;
            case BLACK:
                activeTeam = Team.WHITE;
                break;
            case NONE:
                break;
        }
    }

    public ChessBox search(String position) {
        return grid.get(position);
    }
}