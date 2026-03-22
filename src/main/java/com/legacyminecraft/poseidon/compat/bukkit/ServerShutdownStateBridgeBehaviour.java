package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;

/**
 * Canonical compat bridge for checking legacy CraftBukkit shutdown state.
 */
public final class ServerShutdownStateBridgeBehaviour {
    private static final ServerShutdownStateBridgeBehaviour INSTANCE = new ServerShutdownStateBridgeBehaviour();

    private ServerShutdownStateBridgeBehaviour() {
    }

    public static ServerShutdownStateBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isShuttingDown(Server server) {
        if (!(server instanceof CraftServer)) {
            return false;
        }
        return ((CraftServer) server).isShuttingdown();
    }
}
