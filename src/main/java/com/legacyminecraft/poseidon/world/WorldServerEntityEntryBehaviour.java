package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;

/**
 * Canonical behaviour for world-server entity-entry gating rules.
 */
public final class WorldServerEntityEntryBehaviour {
    private static final WorldServerEntityEntryBehaviour INSTANCE = new WorldServerEntityEntryBehaviour();

    private WorldServerEntityEntryBehaviour() {
    }

    public static WorldServerEntityEntryBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldEnterWorld(Entity entity) {
        return entity.passenger == null || !(entity.passenger instanceof EntityHuman);
    }
}
