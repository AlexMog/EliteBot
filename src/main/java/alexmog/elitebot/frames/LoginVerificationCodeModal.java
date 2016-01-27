package alexmog.elitebot.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alexmog.elitebot.Main;
import alexmog.elitebot.api.Api.Status;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginVerificationCodeModal extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 7271340010195671365L;
    private final JPanel contentPanel = new JPanel();
    private JTextField verificationCode;
    private LoginFrame mParent;
    private JLabel statusLabel = new JLabel("");

    /**
     * Create the dialog.
     */
    public LoginVerificationCodeModal(LoginFrame parent) {
        super(parent, "Verification code needed", true);
        this.mParent = parent;
        setBounds(100, 100, 450, 224);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblYouHaveReceived = new JLabel("<html><body><center>You have received a confirmation code in your mailbox.<br />\r\nPlease, copy this code here to allow EliteBot to access to your<br />\r\nIn-game datas</html></body></center>");
            lblYouHaveReceived.setHorizontalAlignment(SwingConstants.CENTER);
            lblYouHaveReceived.setBounds(12, 13, 408, 46);
            contentPanel.add(lblYouHaveReceived);
        }
        {
            verificationCode = new JTextField();
            verificationCode.setBounds(158, 72, 116, 22);
            contentPanel.add(verificationCode);
            verificationCode.setColumns(10);
        }
        
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBounds(12, 107, 408, 16);
        contentPanel.add(statusLabel);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                final JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
                okButton.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent e) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                okButton.setEnabled(false);
                                try {
                                    Status status = Main.API.submitVerification(verificationCode.getText());
                                    if (status != Status.Ok) {
                                        okButton.setEnabled(false);
                                        statusLabel.setText("Error: bad verification code.");
                                    } else if (status == Status.Ok) {
                                        LoginVerificationCodeModal.this.setVisible(false);
                                        mParent.verified();
                                    } else {
                                        statusLabel.setText("Error: Unknow error.");
                                    }
                                    okButton.setEnabled(true);
                                } catch (IOException | URISyntaxException e) {
                                    statusLabel.setText("Error: " + e.getMessage());
                                    e.printStackTrace();
                                }
                                okButton.setEnabled(true);
                            }
                        }, "Verification thread").start();
                    }
                });
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LoginVerificationCodeModal.this.setVisible(false);
                    }
                });
            }
        }
    }
}
