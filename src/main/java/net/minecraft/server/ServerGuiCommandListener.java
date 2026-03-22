package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.gui.ServerGuiEventBehaviour;

import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ServerGuiCommandListener implements ActionListener {
    private static final ServerGuiEventBehaviour SERVER_GUI_EVENT_BEHAVIOUR = ServerGuiEventBehaviour.getInstance();

    final JTextField a;

    final ServerGUI b;

    ServerGuiCommandListener(ServerGUI servergui, JTextField jtextfield) {
        this.b = servergui;
        this.a = jtextfield;
    }

    public void actionPerformed(ActionEvent actionevent) {
        SERVER_GUI_EVENT_BEHAVIOUR.onCommandSubmitted(this.a, ServerGUI.a(this.b), this.b);
    }
}
