package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting Bukkit server views to CraftServer wrappers.
 */
public final class ServerWrapperProjectionBridgeBehaviour {
    private static final ServerWrapperProjectionBridgeBehaviour INSTANCE = new ServerWrapperProjectionBridgeBehaviour();

    private ServerWrapperProjectionBridgeBehaviour() {
    }

    public static ServerWrapperProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftServer resolveCraftServer(Object server) {
        if (server instanceof CraftServer) {
            return (CraftServer) server;
        }
        return null;
    }
}
