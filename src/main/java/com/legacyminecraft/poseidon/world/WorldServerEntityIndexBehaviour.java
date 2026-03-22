package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityList;

/**
 * Canonical behaviour for world-server entity-id index bookkeeping.
 */
public final class WorldServerEntityIndexBehaviour {
    private static final WorldServerEntityIndexBehaviour INSTANCE = new WorldServerEntityIndexBehaviour();

    private WorldServerEntityIndexBehaviour() {
    }

    public static WorldServerEntityIndexBehaviour getInstance() {
        return INSTANCE;
    }

    public void indexEntity(EntityList entityIndex, Entity entity) {
        entityIndex.a(entity.id, entity);
    }

    public void unindexEntity(EntityList entityIndex, Entity entity) {
        entityIndex.d(entity.id);
    }

    public Entity getById(EntityList entityIndex, int entityId) {
        return (Entity) entityIndex.a(entityId);
    }
}
