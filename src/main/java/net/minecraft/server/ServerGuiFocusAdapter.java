package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.gui.ServerGuiFocusBridgeBehaviour;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class ServerGuiFocusAdapter extends FocusAdapter {
    private static final ServerGuiFocusBridgeBehaviour SERVER_GUI_FOCUS_BRIDGE_BEHAVIOUR = ServerGuiFocusBridgeBehaviour.getInstance();

    final ServerGUI a;

    ServerGuiFocusAdapter(ServerGUI servergui) {
        this.a = servergui;
    }

    public void focusGained(FocusEvent focusevent) {
        SERVER_GUI_FOCUS_BRIDGE_BEHAVIOUR.onFocusGained(focusevent);
    }
}
