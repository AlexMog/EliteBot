package alexmog.elitebot.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alexmog.elitebot.Main;

import javax.swing.JTextPane;

public class WelcomeFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 512414531101797741L;
    private JPanel contentPane;
    private JTextPane welcomemsg = new JTextPane();

    /**
     * Create the frame.
     * @throws IOException 
     */
    public WelcomeFrame() throws IOException {
        setBounds(100, 100, 851, 591);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        welcomemsg.setEditable(false);
        contentPane.add(welcomemsg, BorderLayout.CENTER);
        setWelcomeMessage();
        addWindowListener(new WindowListener() {
            
            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                Main.loginFrame.setVisible(true);
            }
            
            @Override
            public void windowClosed(WindowEvent e) {
            }
            
            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    private void setWelcomeMessage() throws IOException {
        byte[] data = Files.readAllBytes(Paths.get("welcome.txt"));
        welcomemsg.setText(new String(data));
    }

}
