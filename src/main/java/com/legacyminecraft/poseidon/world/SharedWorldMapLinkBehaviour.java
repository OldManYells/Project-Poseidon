package com.legacyminecraft.poseidon.world;

import net.minecraft.server.WorldServer;

/**
 * Canonical behaviour for sharing map collections between primary and secondary worlds.
 */
public final class SharedWorldMapLinkBehaviour {
    private static final SharedWorldMapLinkBehaviour INSTANCE = new SharedWorldMapLinkBehaviour();

    private SharedWorldMapLinkBehaviour() {
    }

    public static SharedWorldMapLinkBehaviour getInstance() {
        return INSTANCE;
    }

    public void linkSharedMaps(WorldServer secondaryWorld, WorldServer primaryWorld) {
        secondaryWorld.worldMaps = primaryWorld.worldMaps;
    }
}
