package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.gui.ServerGuiEventBehaviour;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class ServerWindowAdapter extends WindowAdapter {
    private static final ServerGuiEventBehaviour SERVER_GUI_EVENT_BEHAVIOUR = ServerGuiEventBehaviour.getInstance();

    final MinecraftServer a;

    ServerWindowAdapter(MinecraftServer minecraftserver) {
        this.a = minecraftserver;
    }

    public void windowClosing(WindowEvent windowevent) {
        SERVER_GUI_EVENT_BEHAVIOUR.onWindowClosing(this.a);
    }
}
