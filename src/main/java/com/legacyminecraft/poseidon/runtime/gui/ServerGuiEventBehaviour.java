package com.legacyminecraft.poseidon.runtime.gui;

import net.minecraft.server.ICommandListener;
import net.minecraft.server.MinecraftServer;

import javax.swing.JTextField;

public final class ServerGuiEventBehaviour {
    private static final ServerGuiEventBehaviour INSTANCE = new ServerGuiEventBehaviour();

    private ServerGuiEventBehaviour() {
    }

    public static ServerGuiEventBehaviour getInstance() {
        return INSTANCE;
    }

    public void onCommandSubmitted(JTextField commandField, MinecraftServer server, ICommandListener commandSource) {
        String command = commandField.getText().trim();

        if (command.length() > 0) {
            server.issueCommand(command, commandSource);
        }

        commandField.setText("");
    }

    public void onWindowClosing(MinecraftServer server) {
        server.a();

        while (!server.isStopped) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }

        System.exit(0);
    }
}
