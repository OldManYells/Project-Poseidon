package com.legacyminecraft.compat.bukkit;


import java.util.UUID;

/**
 * Canonical behavior for CraftWorld identity, seed, and wrapper string projections.
 */
public final class CraftWorldIdentityBehaviour {
    private static final CraftWorldIdentityBehaviour INSTANCE = new CraftWorldIdentityBehaviour();

    private CraftWorldIdentityBehaviour() {
    }

    public static CraftWorldIdentityBehaviour getInstance() {
        return INSTANCE;
    }

    public String getName(WorldServer worldServer) {
        return worldServer.worldData.name;
    }

    public UUID getUID(WorldServer worldServer) {
        return worldServer.getUUID();
    }

    public long getId(WorldServer worldServer) {
        return worldServer.worldData.getSeed();
    }

    public long getSeed(WorldServer worldServer) {
        return worldServer.worldData.getSeed();
    }

    public String toString(WorldServer worldServer) {
        return "CraftWorld{name=" + getName(worldServer) + '}';
    }
}
