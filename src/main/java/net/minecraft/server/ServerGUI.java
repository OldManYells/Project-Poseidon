package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.gui.ServerGuiBehaviour;

import javax.swing.JComponent;
import java.util.logging.Logger;

public class ServerGUI extends JComponent implements ICommandListener {
    private static final ServerGuiBehaviour SERVER_GUI_BEHAVIOUR = ServerGuiBehaviour.getInstance();

    public static Logger a = Logger.getLogger("Minecraft");
    private MinecraftServer b;

    public static void a(MinecraftServer minecraftserver) {
        SERVER_GUI_BEHAVIOUR.openWindow(minecraftserver, new ServerWindowAdapter(minecraftserver));
    }

    public ServerGUI(MinecraftServer minecraftserver) {
        this.b = minecraftserver;
        SERVER_GUI_BEHAVIOUR.initializeLayout(this, minecraftserver);
        try {
            this.add(this.c(), "Center");
            this.add(this.a(), "West");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private JComponent a() {
        return SERVER_GUI_BEHAVIOUR.createStatsPanel(this, this.b);
    }

    private JComponent b() {
        return SERVER_GUI_BEHAVIOUR.createPlayersPanel(this.b);
    }

    private JComponent c() {
        return SERVER_GUI_BEHAVIOUR.createLogAndChatPanel(this, a, new ServerGuiBehaviour.CommandListenerFactory() {
            public java.awt.event.ActionListener create(javax.swing.JTextField textField) {
                return new ServerGuiCommandListener(ServerGUI.this, textField);
            }
        }, new ServerGuiFocusAdapter(this));
    }

    public void sendMessage(String s) {
        SERVER_GUI_BEHAVIOUR.sendConsoleMessage(a, s);
    }

    public String getName() {
        return SERVER_GUI_BEHAVIOUR.consoleName();
    }

    static MinecraftServer a(ServerGUI servergui) {
        return servergui.b;
    }
}
