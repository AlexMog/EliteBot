package alexmog.elitebot.frames;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import alexmog.elitebot.beans.Ship;

import java.awt.Dimension;

import javax.swing.JLabel;

public class ShipPanel extends JPanel {
    private static final long serialVersionUID = -4069523029309712051L;

    public ShipPanel(Ship ship) {
        setPreferredSize(new Dimension(345, 85));
        setLayout(null);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(12, 13, 176, 2);
        add(separator);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(12, 28, 38, 16);
        add(lblName);
        
        JLabel lblIsAlive = new JLabel("Is Alive:");
        lblIsAlive.setBounds(12, 44, 56, 16);
        add(lblIsAlive);
        
        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setBounds(12, 63, 56, 16);
        add(lblPosition);
        
        JLabel name = new JLabel("New label");
        name.setBounds(62, 28, 271, 16);
        add(name);
        name.setText(ship.name);
        
        JLabel alive = new JLabel("New label");
        alive.setBounds(62, 44, 271, 16);
        add(alive);
        alive.setText(ship.alive ? "yes" : "no");
        
        JLabel position = new JLabel("positon");
        position.setBounds(72, 63, 261, 16);
        add(position);
        if (ship.starsystem != null) {
            position.setText(ship.starsystem.name + "(" + ship.station.name + ")");
        } else {
            position.setText("Unknown");
        }
        
        JLabel shipImage = new JLabel("");
        shipImage.setBounds(265, 13, 68, 66);
        add(shipImage);
    }

}
