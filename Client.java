package chess;

import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Stack;

import chess.components.ChessBox;
import chess.components.MovementMarker;
import chess.pieces.Empty;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Piece.Team;

public class Client {
    public static ChessBox currentBox;
    public static JPanel contentPanel;
    public static final boolean SHOW_ALL_COORDS = false;
    public static final Color SELECTION_COLOR = new Color(255, 153, 204);
    public static Board board = new Board();
    // super unnecessary to hold it in a stack
    public static Stack<ChessBox> movementOptionsStack = new Stack<ChessBox>();
    public static Stack<ChessBox> attackOptionsStack = new Stack<ChessBox>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1200);
        frame.setLayout(new BorderLayout(0, 0));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setResizable(false);

        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setSize(1800, 1200);
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20, false));
        frame.add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .5;
        c.weighty = .5;
        c.anchor = GridBagConstraints.CENTER;

        JPanel chessboardPanel = new JPanel(new GridLayout(8, 8, 0, 0));
        chessboardPanel.setPreferredSize(new Dimension(1024, 832));
        chessboardPanel.setBackground(Color.BLACK);
        contentPanel.add(chessboardPanel, c);

        GridBagConstraints x = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.weighty = .5;
        c.anchor = GridBagConstraints.CENTER;

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.ORANGE);
        infoPanel.setSize(100, 1000);
        contentPanel.add(infoPanel, c);

        // make the board
        board.assemble();
        for (ChessBox box : board.grid.values()) {
            chessboardPanel.add(box);
        }

        frame.setVisible(true);
    }

    public static void highlightSelectedPiece(ChessBox newBox) {
        System.out.println("BOX CLICKED: " + newBox.chessPiece.coordinates());
        if (currentBox == newBox) {
            return;
        }

        if (currentBox != null && newBox.chessPiece.team == currentBox.chessPiece.team) {
            // If a piece from the same team is selected, deselect the current piece
            refreshMovementOptions();
            deselectBox(currentBox);
            currentBox = newBox;
            selectBox(newBox);
            displayMovementOptions();
            return;
        }

        if (currentBox != null && !movementOptionsStack.contains(newBox) && !attackOptionsStack.contains(newBox)) {
            // If an empty tile that's not a valid move is clicked, deselect the current
            // piece
            refreshMovementOptions();
            deselectBox(currentBox);
            currentBox = null;
            return;
        }

        if (currentBox == null) {
            if (newBox.chessPiece instanceof Empty) {
                return;
            } else {
                if (newBox.chessPiece.team != board.activeTeam) {
                    return;
                } else {
                    refreshMovementOptions();
                    selectBox(newBox);
                    currentBox = newBox;
                    displayMovementOptions();
                    return;
                }
            }
        } else {
            if (currentBox.chessPiece.move() != null && currentBox.chessPiece.move().contains(newBox.chessPiece.coords)
                    || currentBox.chessPiece.attack() != null
                            && currentBox.chessPiece.attack().contains(newBox.chessPiece.coords)) {
                board.move(currentBox, newBox);
                deselectBox(currentBox);
                currentBox = null;
                refreshMovementOptions();
            }
        }
    }

    public static void selectBox(ChessBox box) {
        box.setBorder(BorderFactory.createLineBorder(SELECTION_COLOR, 5, false));
        box.revalidate();
        box.repaint();
        box.active = true;
    }

    public static void deselectBox(ChessBox box) {
        box.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        box.revalidate();
        box.repaint();
        box.active = false;
    }

    public static void displayMovementOptions() {
        if (currentBox.chessPiece instanceof Empty) {
            return;
        }

        // Refresh previous markers before setting new ones
        refreshMovementOptions();

        movementOptionsStack = new Stack<ChessBox>();
        attackOptionsStack = new Stack<ChessBox>();

        for (Coordinates coordinate : currentBox.chessPiece.move()) {
            if (board.grid.containsKey(coordinate.position)) {
                ChessBox box = board.grid.get(coordinate.position);
                movementOptionsStack.add(box);
                box.add(new MovementMarker(false));
                box.revalidate();
                box.repaint();
            }
        }
        for (Coordinates coordinate : currentBox.chessPiece.attack()) {
            if (board.grid.containsKey(coordinate.position)
                    && !(board.search(coordinate.position).chessPiece instanceof Empty)
                    && board.search(coordinate.position).chessPiece.team != currentBox.chessPiece.team) {
                ChessBox box = board.grid.get(coordinate.position);
                attackOptionsStack.add(board.grid.get(coordinate.position));
                box.add(new MovementMarker(true));
                box.revalidate();
                box.repaint();
            }
        }
    }

    public static void refreshMovementOptions() {
        if (movementOptionsStack.size() > 0) {
            for (ChessBox box : movementOptionsStack) {
                for (Component c : box.getComponents()) {
                    if (c instanceof MovementMarker) {
                        box.remove(c);
                        box.revalidate();
                        box.repaint();
                    }
                }
            }
        }

        if (attackOptionsStack.size() > 0) {
            for (ChessBox box : attackOptionsStack) {
                for (Component c : box.getComponents()) {
                    if (c instanceof MovementMarker) {
                        box.remove(c);
                        box.revalidate();
                        box.repaint();
                    }
                }
            }
        }
        movementOptionsStack.clear();
        attackOptionsStack.clear();
    }
}
