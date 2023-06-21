package chess.components;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MovementMarker extends JLabel {
    ImageIcon moveIcon = new ImageIcon(
            new ImageIcon("chess/icons/MoveMarker.png").getImage().getScaledInstance(50, 50,
                    Image.SCALE_SMOOTH));

    ImageIcon attackIcon = new ImageIcon(
            new ImageIcon("chess/icons/AttackMarker.png").getImage().getScaledInstance(50, 50,
                    Image.SCALE_SMOOTH));

    public MovementMarker(boolean attack) {
        this.setIcon(attack ? attackIcon : moveIcon);
        this.setPreferredSize(new Dimension(75, 75));
        this.setBounds(110, 0, 75, 75);
    }
}
