package alexmog.elitebot.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alexmog.elitebot.Main;
import alexmog.elitebot.api.Api.Status;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 5366760071093087377L;
    private JPanel contentPane;
    private JTextField username;
    private JPasswordField password;
    private JLabel status = new JLabel("");
    private LoginVerificationCodeModal modal = new LoginVerificationCodeModal(this);

    public LoginFrame() {
        setTitle("Login to Elite Dangerous iOS API");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 232, 193);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        username = new JTextField();
        username.setBounds(85, 16, 116, 22);
        contentPane.add(username);
        username.setColumns(10);
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(12, 19, 90, 16);
        contentPane.add(lblUsername);
        
        password = new JPasswordField();
        password.setBounds(85, 51, 116, 22);
        contentPane.add(password);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(12, 58, 66, 16);
        contentPane.add(lblPassword);
        
        final JButton btnConnect = new JButton("Connect");
        btnConnect.setBounds(58, 86, 97, 25);
        contentPane.add(btnConnect);
        
        status.setHorizontalAlignment(SwingConstants.CENTER);
        status.setBounds(12, 117, 189, 16);
        contentPane.add(status);
        
        btnConnect.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                btnConnect.setEnabled(false);
                setStatusText("Connecting to Frontier API...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String passwd = new String(password.getPassword());
                        System.out.println("Connecting with ids: " + username.getText() + " : " + passwd);
                        try {
                            Status status = Main.API.authenticate(username.getText(), passwd);
                            System.out.println("API status: " + status);
                            if (status == Status.Ok) verified();
                            else if (status == Status.NeedVerification)  modal.setVisible(true);
                            else if (status == Status.Ko) {
                                setStatusText("Error: Bad login.");
                                btnConnect.setEnabled(true);
                            }
                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }, "Login thread").start();
            }
        });
    }
    
    public void setStatusText(String text) {
        status.setText(text);
    }
    
    public void verified() {
        status.setText("Logged in! Loading profile...");
        try {
            new ProfileFrame(Main.API.getProfile()).setVisible(true);
            setVisible(false);
        } catch (Exception e) {
            status.setText("Internal error.");
            e.printStackTrace();
        }
    }
}
