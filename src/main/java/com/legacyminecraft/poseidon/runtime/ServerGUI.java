package com.legacyminecraft.poseidon.runtime;

import javax.swing.JPanel;

/**
 * Runtime-local GUI command listener scaffold.
 */
public class ServerGUI extends JPanel implements ICommandListener {
    private final MinecraftServer minecraftServer;

    public ServerGUI(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }

    @Override
    public String getName() {
        return "GUI";
    }

    @Override
    public void sendMessage(String message) {
    }
}
