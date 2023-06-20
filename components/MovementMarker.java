package chess.components;

import java.awt.Color;

import javax.swing.JLabel;

public class MovementMarker extends JLabel {

    Color attack = Color.RED;
    Color move = Color.GREEN;

    public MovementMarker(boolean attack) {
        this.setText(attack ? "ATTACK HERE!" : "MOVE HERE!");
    }
}
