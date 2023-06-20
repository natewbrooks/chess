package chess.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import chess.Client;
import chess.pieces.Empty;
import chess.pieces.Piece;

public class ChessBox extends JPanel implements MouseListener {

    JLabel label;

    Color colorOne = new Color(125, 148, 93);
    Color colorTwo = new Color(238, 238, 213);

    public Piece chessPiece;
    public boolean active = false;

    public ChessBox(int row, int col) {
        addMouseListener(this);
        chessPiece = new Empty(row, col);
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(new Dimension(5, 5));
        this.setBackground((row % 2 == 0 && col % 2 == 1 || (row % 2 == 1 && col % 2 == 0)) ? colorOne : colorTwo);
        if (Client.SHOW_ALL_COORDS) {
            createLabel(chessPiece.coords);
        } else {
            if (row == 1) {
                if (col == 1) {
                    // IF ITS THE CORNER
                    createLabel(chessPiece.coords.charCol);
                    createLabel(chessPiece.coords.row);
                    return;
                }
                // CHARS 'A-H'
                createLabel(chessPiece.coords.charCol);
                // NUMBERS
            } else if (col == 1) {
                createLabel(chessPiece.coords.row);

            }
        }

    }

    public <T> void createLabel(T data) {
        label = new JLabel("" + data);
        label.setForeground(this.getBackground() == colorOne ? colorTwo : colorOne);
        label.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        JPanel panel = new JPanel();
        panel.setOpaque(false); // This makes the panel transparent
        panel.add(label);

        if (data instanceof Integer) { // If it is a number, it goes to the top left
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.add(panel, BorderLayout.NORTH);
        } else { // If it is a character, it goes to the bottom right
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.add(panel, BorderLayout.SOUTH);
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        if (!active) {
            JPanel parent = (JPanel) e.getSource();
            parent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, false));
            parent.revalidate();
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        Client.highlightSelectedPiece(this);
        // JPanel parent = (JPanel) e.getSource();
        // parent.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5, false));
        // parent.revalidate();

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        Client.highlightSelectedPiece(this);
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        return;
        // JPanel parent = (JPanel) e.getSource();
        // parent.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // parent.revalidate();
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        if (!active) {
            JPanel parent = (JPanel) e.getSource();
            parent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            parent.revalidate();
        }
    }

}