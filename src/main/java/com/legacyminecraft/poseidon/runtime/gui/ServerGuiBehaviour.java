package com.legacyminecraft.poseidon.runtime.gui;

import net.minecraft.server.GuiLogOutputHandler;
import net.minecraft.server.GuiStatsComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerListBox;
import net.minecraft.server.ServerGUI;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;
import java.util.logging.Logger;

public final class ServerGuiBehaviour {
    private static final ServerGuiBehaviour INSTANCE = new ServerGuiBehaviour();

    private ServerGuiBehaviour() {
    }

    public static ServerGuiBehaviour getInstance() {
        return INSTANCE;
    }

    public interface CommandListenerFactory {
        ActionListener create(JTextField textField);
    }

    public void openWindow(MinecraftServer minecraftserver, WindowListener windowListener) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            // Ignore look-and-feel errors and continue with default UI.
        }

        ServerGUI servergui = new ServerGUI(minecraftserver);
        JFrame jframe = new JFrame("Minecraft server");

        jframe.add(servergui);
        jframe.pack();
        jframe.setLocationRelativeTo((Component) null);
        jframe.setVisible(true);
        jframe.addWindowListener(windowListener);
    }

    public void initializeLayout(ServerGUI serverGui, MinecraftServer minecraftServer) {
        serverGui.setPreferredSize(new Dimension(854, 480));
        serverGui.setLayout(new BorderLayout());
    }

    public JComponent createStatsPanel(ServerGUI serverGui, MinecraftServer minecraftServer) {
        JPanel jpanel = new JPanel(new BorderLayout());

        jpanel.add(new GuiStatsComponent(), "North");
        jpanel.add(this.createPlayersPanel(minecraftServer), "Center");
        jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
        return jpanel;
    }

    public JComponent createPlayersPanel(MinecraftServer minecraftServer) {
        PlayerListBox playerlistbox = new PlayerListBox(minecraftServer);
        JScrollPane jscrollpane = new JScrollPane(playerlistbox, 22, 30);

        jscrollpane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
        return jscrollpane;
    }

    public JComponent createLogAndChatPanel(ServerGUI serverGui, Logger logger, CommandListenerFactory commandListenerFactory, FocusListener focusListener) {
        JPanel jpanel = new JPanel(new BorderLayout());
        JTextArea jtextarea = new JTextArea();

        logger.addHandler(new GuiLogOutputHandler(jtextarea));
        JScrollPane jscrollpane = new JScrollPane(jtextarea, 22, 30);

        jtextarea.setEditable(false);
        JTextField jtextfield = new JTextField();

        jtextfield.addActionListener(commandListenerFactory.create(jtextfield));
        jtextarea.addFocusListener(focusListener);
        jpanel.add(jscrollpane, "Center");
        jpanel.add(jtextfield, "South");
        jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
        return jpanel;
    }

    public void sendConsoleMessage(Logger logger, String message) {
        logger.info(message);
    }

    public String consoleName() {
        return "CONSOLE";
    }
}
