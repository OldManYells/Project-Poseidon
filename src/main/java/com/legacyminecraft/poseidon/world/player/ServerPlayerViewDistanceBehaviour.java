package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.MinecraftServer;

/**
 * Canonical policy for resolving furthest viewable block distance from server state.
 */
public final class ServerPlayerViewDistanceBehaviour {
    private static final ServerPlayerViewDistanceBehaviour INSTANCE = new ServerPlayerViewDistanceBehaviour();

    private ServerPlayerViewDistanceBehaviour() {
    }

    public static ServerPlayerViewDistanceBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveFurthestViewableBlock(MinecraftServer server) {
        if (server.worlds.size() == 0) {
            return server.propertyManager.getInt("view-distance", 10) * 16 - 16;
        }
        return server.worlds.get(0).manager.getFurthestViewableBlock();
    }
}
