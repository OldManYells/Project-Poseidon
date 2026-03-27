package com.legacyminecraft.compat.bukkit;


import java.util.List;

/**
 * Canonical bridge behaviour for CraftPlayer server-context projection.
 */
public final class PlayerServerContextBridgeBehaviour {
    private static final PlayerServerContextBridgeBehaviour INSTANCE = new PlayerServerContextBridgeBehaviour();

    private PlayerServerContextBridgeBehaviour() {
    }

    public static PlayerServerContextBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T resolveServerHandle(Object server) {
        return BridgeReflection.cast(BridgeReflection.invoke(server, "getHandle"));
    }

    public List resolveOnlinePlayers(Object server) {
        Object handle = BridgeReflection.invoke(server, "getHandle");
        return BridgeReflection.cast(BridgeReflection.getField(handle, "players"));
    }
}
