package chess;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JLabel;

import chess.components.ChessBox;
import chess.pieces.Pawn;
import chess.pieces.Piece.Team;

public class Board {
    public Map<String, ChessBox> grid = new LinkedHashMap<String, ChessBox>();
    public int current_round = 1;
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
            grid.get(s + 2).chessPiece = new Pawn(grid.get(s + 2).chessPiece.coords);
            grid.get(s + 2).add(new JLabel("WHITE PAWN!"));

            grid.get(s + 7).chessPiece = new Pawn(grid.get(s + 7).chessPiece.coords);
            grid.get(s + 7).chessPiece.team = Team.BLACK;
            grid.get(s + 7).add(new JLabel("BLACK PAWN!"));
        }
    }
}