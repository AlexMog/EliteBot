package alexmog.elitebot.frames;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alexmog.elitebot.Main;
import alexmog.elitebot.beans.Profile;
import alexmog.elitebot.beans.Ship;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JTable;

public class ProfileFrame extends JFrame {
    private static final long serialVersionUID = -4790122086083164931L;
    private final DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");

    private JPanel contentPane;
    private JTextField edPath;
    private JTable table;
    public ProfileFrame(Profile profile) {
        setTitle("EliteBot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 705, 400);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("CMDR Datas", null, panel, null);
        panel.setLayout(null);
        
        JLabel lblId = new JLabel("Id:");
        lblId.setBounds(12, 13, 16, 16);
        panel.add(lblId);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(12, 30, 38, 16);
        panel.add(lblName);
        
        JLabel lblNewLabel = new JLabel("Credits:");
        lblNewLabel.setBounds(12, 48, 45, 16);
        panel.add(lblNewLabel);
        
        JLabel lblDebt = new JLabel("Debt:");
        lblDebt.setBounds(12, 66, 38, 16);
        panel.add(lblDebt);
        
        JLabel lblIsAlive = new JLabel("Is Alive:");
        lblIsAlive.setBounds(12, 87, 46, 16);
        panel.add(lblIsAlive);
        
        JLabel lblIsDocked = new JLabel("Is Docked:");
        lblIsDocked.setBounds(12, 105, 66, 16);
        panel.add(lblIsDocked);
        
        JLabel combatImage = new JLabel("Cannot load image");
        combatImage.setBounds(275, 34, 123, 123);
        panel.add(combatImage);
        try {
            combatImage.setIcon(new ImageIcon(ImageIO.read(new File("images/combat/rank-" + profile.commander.rank.combat + "-combat.png"))
                    .getScaledInstance(123, 123, 1)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        JLabel cqcImage = new JLabel("Cannot load image");
        cqcImage.setBounds(524, 30, 123, 123);
        panel.add(cqcImage);
        try {
            cqcImage.setIcon(new ImageIcon(ImageIO.read(new File("images/cqc/rank-" + profile.commander.rank.combat + "-cqc.png"))
                    .getScaledInstance(123, 123, 1)));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        
        JLabel tradingImage = new JLabel("Cannot load image");
        tradingImage.setBounds(524, 177, 123, 123);
        panel.add(tradingImage);
        try {
            tradingImage.setIcon(new ImageIcon(ImageIO.read(new File("images/trading/rank-" + profile.commander.rank.combat + "-trading.png"))
                    .getScaledInstance(123, 123, 1)));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        JLabel explorerImage = new JLabel("Cannot load image");
        explorerImage.setBounds(275, 177, 123, 123);
        panel.add(explorerImage);
        try {
            explorerImage.setIcon(new ImageIcon(ImageIO.read(new File("images/explorer/rank-" + profile.commander.rank.combat + "-explorer.png"))
                    .getScaledInstance(123, 123, 1)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        JLabel idLabel = new JLabel("idLabel");
        idLabel.setBounds(36, 13, 227, 16);
        panel.add(idLabel);
        idLabel.setText(profile.commander.id + "");
        
        JLabel nameLabel = new JLabel("nameLabel");
        nameLabel.setBounds(62, 30, 201, 16);
        panel.add(nameLabel);
        nameLabel.setText(profile.commander.name);
        
        JLabel creditsLabel = new JLabel("creditsLabel");
        creditsLabel.setBounds(69, 48, 194, 16);
        panel.add(creditsLabel);
        creditsLabel.setText(mDecimalFormat.format(profile.commander.credits));
        
        JLabel debtLabel = new JLabel("debtLabel");
        debtLabel.setBounds(50, 66, 213, 16);
        panel.add(debtLabel);
        debtLabel.setText(mDecimalFormat.format(profile.commander.debt));
        
        JLabel aliveLabel = new JLabel("aliveLabel");
        aliveLabel.setBounds(70, 87, 66, 16);
        panel.add(aliveLabel);
        aliveLabel.setText(profile.commander.alive ? "yes" : "no");
        
        JLabel isDockedLabel = new JLabel("isDockedLabel");
        isDockedLabel.setBounds(80, 105, 66, 16);
        panel.add(isDockedLabel);
        isDockedLabel.setText(profile.commander.docked ? "yes" : "no");
        
        JLabel cRank = new JLabel("Combat rank: " + profile.commander.rank.combat);
        cRank.setBounds(275, 13, 123, 16);
        panel.add(cRank);
        
        JLabel eRank = new JLabel("Explorer rank: " + profile.commander.rank.explore);
        eRank.setBounds(275, 159, 123, 16);
        panel.add(eRank);
        
        JLabel cqcRank = new JLabel("CQC Rank: " + profile.commander.rank.cqc);
        cqcRank.setBounds(524, 13, 123, 16);
        panel.add(cqcRank);
        
        JLabel tRank = new JLabel("Trader rank: " + profile.commander.rank.trade);
        tRank.setBounds(524, 159, 123, 16);
        panel.add(tRank);
        
        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Last Starport", null, panel_2, null);
        panel_2.setLayout(null);
        
        JLabel lblSystemDatas = new JLabel("System datas:");
        lblSystemDatas.setBounds(12, 13, 129, 16);
        panel_2.add(lblSystemDatas);
        
        JLabel lblSystemId = new JLabel("System id:");
        lblSystemId.setBounds(12, 31, 61, 16);
        panel_2.add(lblSystemId);
        
        JLabel lblSystemName = new JLabel("System name:");
        lblSystemName.setBounds(12, 47, 83, 16);
        panel_2.add(lblSystemName);
        
        JLabel lblSystemFaction = new JLabel("System faction:");
        lblSystemFaction.setBounds(12, 60, 89, 16);
        panel_2.add(lblSystemFaction);
        
        JLabel systId = new JLabel("id");
        systId.setBounds(85, 31, 316, 16);
        panel_2.add(systId);
        systId.setText(profile.lastSystem.id);
        
        JLabel systName = new JLabel("name");
        systName.setBounds(107, 47, 294, 16);
        panel_2.add(systName);
        systName.setText(profile.lastSystem.name);
        
        JLabel systFaction = new JLabel("faction");
        systFaction.setBounds(107, 60, 294, 16);
        panel_2.add(systFaction);
        systFaction.setText(profile.lastSystem.faction);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(12, 89, 743, 2);
        panel_2.add(separator);
        
        JLabel lblStarportDatas = new JLabel("Starport datas:");
        lblStarportDatas.setBounds(12, 98, 210, 16);
        panel_2.add(lblStarportDatas);
        
        JLabel lblStarportId = new JLabel("Starport id:");
        lblStarportId.setBounds(12, 115, 66, 16);
        panel_2.add(lblStarportId);
        
        JLabel lblStarportName = new JLabel("Starport name:");
        lblStarportName.setBounds(12, 129, 89, 16);
        panel_2.add(lblStarportName);
        
        JLabel lblStarportFaction = new JLabel("Starport faction:");
        lblStarportFaction.setBounds(12, 144, 99, 16);
        panel_2.add(lblStarportFaction);
        
        JLabel starportId = new JLabel("id");
        starportId.setBounds(85, 115, 337, 16);
        panel_2.add(starportId);
        starportId.setText(profile.lastStarport.id);
        
        JLabel starportName = new JLabel("name");
        starportName.setBounds(107, 129, 337, 16);
        panel_2.add(starportName);
        starportName.setText(profile.lastStarport.name);
        
        JLabel starportFaction = new JLabel("faction");
        starportFaction.setBounds(117, 144, 337, 16);
        panel_2.add(starportFaction);
        starportFaction.setText(profile.lastStarport.faction);
        
        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Fleet", null, panel_3, null);
        panel_3.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        panel_3.add(scrollPane);
        
        JPanel panel_4 = new JPanel();
        scrollPane.setViewportView(panel_4);
        panel_4.setLayout(new GridLayout(0, 1));
        for (Ship s : profile.ships.values()) {
            panel_4.add(new ShipPanel(s));
        }
        panel_4.validate();
        
        JPanel panel_5 = new JPanel();
        tabbedPane.addTab("Flight log", null, panel_5, null);
        panel_5.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_1 = new JScrollPane();
        panel_5.add(scrollPane_1);
        
        table = new JTable();
        table.setFillsViewportHeight(true);
        scrollPane_1.setViewportView(table);
        
        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Options", null, panel_1, null);
        panel_1.setLayout(null);
        
        JButton btnRefreshDatas = new JButton("Refresh datas");
        btnRefreshDatas.setEnabled(false);
        btnRefreshDatas.setBounds(12, 13, 121, 25);
        panel_1.add(btnRefreshDatas);
        
        JCheckBox sendMarketDatas = new JCheckBox("Send market-datas to EDDB");
        sendMarketDatas.setEnabled(false);
        sendMarketDatas.setBounds(12, 47, 187, 25);
        panel_1.add(sendMarketDatas);
        
        JLabel lblEliteDangerousLauncher = new JLabel("Elite Dangerous launcher path:");
        lblEliteDangerousLauncher.setEnabled(false);
        lblEliteDangerousLauncher.setBounds(12, 119, 176, 16);
        panel_1.add(lblEliteDangerousLauncher);
        
        edPath = new JTextField();
        edPath.setEnabled(false);
        edPath.setBounds(200, 116, 460, 22);
        panel_1.add(edPath);
        edPath.setColumns(10);
        
        JCheckBox chckbxEnableSystemsTracking = new JCheckBox("Enable solar systems tracking");
        chckbxEnableSystemsTracking.setEnabled(false);
        chckbxEnableSystemsTracking.setBounds(12, 85, 197, 25);
        panel_1.add(chckbxEnableSystemsTracking);
        
        JButton saveConfig = new JButton("Save");
        saveConfig.setBounds(261, 275, 150, 25);
        panel_1.add(saveConfig);
        
        JCheckBox chckbxEnableFlightLog = new JCheckBox("Enable flight log");
        chckbxEnableFlightLog.setEnabled(false);
        chckbxEnableFlightLog.setBounds(8, 144, 652, 25);
        panel_1.add(chckbxEnableFlightLog);
        validate();
    }
}
