package com.legacyminecraft.poseidon.runtime.gui;

import javax.swing.JList;

/**
 * GUI-local player list widget.
 */
public class PlayerListBox extends JList implements com.legacyminecraft.poseidon.runtime.IUpdatePlayerListBox {
    private static final long serialVersionUID = 1L;
    private final MinecraftServer minecraftServer;
    private int tickCounter;

    public PlayerListBox(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    @Override
    public void a() {
        tickCounter = PlayerListBoxBehaviour.getInstance().updateIfDue(minecraftServer, tickCounter, this);
    }
}
