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

public class Client {
    public static ChessBox currentBox;
    public static final boolean SHOW_ALL_COORDS = false;
    public static final Color SELECTION_COLOR = new Color(11, 97, 45);
    public static Board board = new Board();
    // super unnecessary to hold it in a stack
    public static Stack<ChessBox> movementOptionsStack = new Stack<ChessBox>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1200);
        frame.setLayout(new BorderLayout(0, 0));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setResizable(false);

        JPanel content = new JPanel(new GridBagLayout());
        content.setSize(1800, 1200);
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20, false));
        frame.add(content, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .5;
        c.weighty = .5;
        c.anchor = GridBagConstraints.CENTER;

        JPanel chessboardPanel = new JPanel(new GridLayout(8, 8, 0, 0));
        chessboardPanel.setPreferredSize(new Dimension(1024, 832));
        chessboardPanel.setBackground(Color.BLACK);
        content.add(chessboardPanel, c);

        GridBagConstraints x = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.weighty = .5;
        c.anchor = GridBagConstraints.CENTER;

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.ORANGE);
        infoPanel.setSize(100, 1000);
        content.add(infoPanel, c);

        // make the board
        board.assemble();
        for (ChessBox box : board.grid.values()) {
            chessboardPanel.add(box);
        }

        frame.setVisible(true);
    }

    public static void highlightSelectedPiece(ChessBox newBox) {
        if (currentBox == newBox) {
            return;
        }

        // MOVE POSITION!
        if (newBox.chessPiece instanceof Empty) {
            if (currentBox != null) {
                if (movementOptionsStack.contains(newBox)) {
                    // do checks for if its black and if you are attacking vs moving
                    Coordinates newCoords = new Coordinates(newBox.chessPiece.coords);
                    newBox.chessPiece = currentBox.chessPiece;
                    newBox.chessPiece.coords = newCoords;
                    // for (Component c : currentBox.getComponents()) {
                    // if (c instanceof JLabel) {
                    // currentBox.remove(c);
                    // currentBox.revalidate();
                    // currentBox.repaint();
                    // }
                    // }
                    currentBox.chessPiece = new Empty(currentBox.chessPiece.coords);
                    refreshMovementOptions();
                    newBox.add(new JLabel("HEY YOU ARE HERE NOW!"));
                    newBox.revalidate();
                    newBox.repaint();
                }
            } else {
                currentBox = null;
                return;
            }
        }

        // IF ITS THE FIRST MOVE OF GAME
        if (currentBox == null) {
            newBox.setBorder(BorderFactory.createLineBorder(SELECTION_COLOR, 5, false));
            newBox.revalidate();
            newBox.repaint();
            newBox.active = true;
            currentBox = newBox;
            displayMovementOptions();
            return;
        }

        // OTHERWISE
        refreshMovementOptions();
        newBox.active = true;
        newBox.setBorder(BorderFactory.createLineBorder(SELECTION_COLOR, 5, false));
        newBox.revalidate();
        currentBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        currentBox.revalidate();
        currentBox.repaint();
        currentBox.active = false;
        currentBox = newBox;
        displayMovementOptions();
    }

    public static void displayMovementOptions() {
        if (currentBox.chessPiece instanceof Empty) {
            return;
        }

        movementOptionsStack = new Stack<ChessBox>();

        for (Coordinates coordinate : currentBox.chessPiece.move()) {
            if (board.grid.containsKey(coordinate.position)) {
                movementOptionsStack.add(board.grid.get(coordinate.position));
                board.grid.get(coordinate.position).add(new MovementMarker(false));
            }
        }
        for (Coordinates coordinate : currentBox.chessPiece.attack()) {
            if (board.grid.containsKey(coordinate.position)) {
                movementOptionsStack.add(board.grid.get(coordinate.position));
                board.grid.get(coordinate.position).add(new MovementMarker(true));
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
        movementOptionsStack.clear();
    }
}
