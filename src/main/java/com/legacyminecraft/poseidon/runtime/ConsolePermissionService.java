package com.legacyminecraft.poseidon.runtime;

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
        return true;
    }

    public boolean checkPermission(MinecraftServer server, ICommandListener listener, String command) {
        if (hasPermission(server, listener, "bukkit.command." + command)) {
            return true;
        }

        listener.sendMessage("You do not have permission to use this command.");
        return false;
    }
}
