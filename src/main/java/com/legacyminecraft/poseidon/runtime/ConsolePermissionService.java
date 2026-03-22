package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.compat.bukkit.CommandSenderBackedListener;
import net.minecraft.server.ICommandListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.ServerGUI;

/**
 * Canonical permission resolver for legacy console command handlers.
 */
public final class ConsolePermissionService {
    private static final ConsolePermissionService INSTANCE = new ConsolePermissionService();

    private ConsolePermissionService() {
    }

    public static ConsolePermissionService getInstance() {
        return INSTANCE;
    }

    public boolean hasPermission(MinecraftServer server, ICommandListener listener, String permissionNode) {
        if (listener instanceof CommandSenderBackedListener) {
            CommandSenderBackedListener commandSenderBackedListener = (CommandSenderBackedListener) listener;
            return commandSenderBackedListener.getSender().hasPermission(permissionNode);
        } else if (listener instanceof NetServerHandler) {
            NetServerHandler net = (NetServerHandler) listener;
            return net.getPlayer().hasPermission(permissionNode);
        } else if ((listener instanceof ServerGUI) || (listener instanceof MinecraftServer)) {
            return server.console.hasPermission(permissionNode);
        }

        return false;
    }

    public boolean checkPermission(MinecraftServer server, ICommandListener listener, String command) {
        if (hasPermission(server, listener, "bukkit.command." + command)) {
            return true;
        }

        listener.sendMessage("I'm sorry, Dave, but I cannot let you do that.");
        return false;
    }
}
