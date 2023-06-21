package chess.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import chess.Client;
import chess.Coordinates;
import chess.pieces.Empty;
import chess.pieces.Piece;
import chess.pieces.Piece.Team;

public class ChessBox extends JLayeredPane implements MouseListener {

    JLabel label;
    JPanel iconPanel;

    // Color colorOne = new Color(125, 148, 93); // green
    // Color colorOne = new Color(112, 144, 161); // blue
    Color colorOne = new Color(139, 112, 169); // purple
    // Color colorTwo = new Color(238, 238, 213); // cream

    Color colorTwo = new Color(230, 224, 207);

    public Piece chessPiece;
    public boolean active = false;

    public ChessBox(int row, int col) {
        addMouseListener(this);
        chessPiece = new Empty(row, col);
        iconPanel = new JPanel();
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(75, 100));
        iconPanel.setBounds(45, 10, 75, 100);
        this.add(iconPanel);
        this.setOpaque(true);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
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
        panel.setPreferredSize(new Dimension(50, 50));
        panel.add(label);

        if (data instanceof Integer) { // If it is a number, it goes to the top left
            panel.setBounds(0, 0, 50, 50);
            this.add(panel);
        } else { // If it is a character, it goes to the bottom right
            panel.setBounds(125, 100, 50, 50);
            this.add(panel);
        }
    }

    public void updateIcon(Piece piece) {
        for (Component c : iconPanel.getComponents()) {
            if (c instanceof Piece) {
                iconPanel.remove(c);
            }
        }
        iconPanel.add(piece);
        iconPanel.revalidate();
        iconPanel.repaint();
    }

    public void swapChessPieces(ChessBox box) {
        Coordinates newCoords = new Coordinates(this.chessPiece.coords);
        Coordinates oldCoords = new Coordinates(box.chessPiece.coords);
        this.chessPiece = box.chessPiece;
        this.chessPiece.coords = newCoords;

        box.chessPiece = new Empty(oldCoords, Team.NONE);
        box.updateIcon(box.chessPiece);
        this.updateIcon(this.chessPiece);
        this.chessPiece.first_move = false;
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        if (!active) {
            JLayeredPane parent = (JLayeredPane) e.getSource();
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
            JLayeredPane parent = (JLayeredPane) e.getSource();
            parent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            parent.revalidate();
        }
    }

}