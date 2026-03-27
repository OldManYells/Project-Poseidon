package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge behaviour for CraftPlayer connection projection.
 */
public final class PlayerConnectionContextBridgeBehaviour {
    private static final PlayerConnectionContextBridgeBehaviour INSTANCE = new PlayerConnectionContextBridgeBehaviour();

    private PlayerConnectionContextBridgeBehaviour() {
    }

    public static PlayerConnectionContextBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T resolveConnection(Object player) {
        return BridgeReflection.cast(BridgeReflection.getField(player, "netServerHandler"));
    }
}
