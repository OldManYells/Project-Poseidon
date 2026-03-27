package com.legacyminecraft.poseidon.world;


import java.util.List;

/**
 * Canonical world/tracker lookup helper for legacy MinecraftServer wrappers.
 */
public final class WorldLookupSystem {
    private static final WorldLookupSystem INSTANCE = new WorldLookupSystem();

    private WorldLookupSystem() {
    }

    public static WorldLookupSystem getInstance() {
        return INSTANCE;
    }

    public WorldServer getWorldServer(List<WorldServer> worlds, int dimension) {
        for (WorldServer world : worlds) {
            if (world.dimension == dimension) {
                return world;
            }
        }

        return worlds.get(0);
    }

    public EntityTracker getTracker(List<WorldServer> worlds, int dimension) {
        return getWorldServer(worlds, dimension).tracker;
    }
}
