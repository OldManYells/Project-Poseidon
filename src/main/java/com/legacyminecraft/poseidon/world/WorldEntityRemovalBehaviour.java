package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;

import java.util.List;

/**
 * Canonical behaviour for world entity-removal lifecycle policy.
 */
public final class WorldEntityRemovalBehaviour {
    private static final WorldEntityRemovalBehaviour INSTANCE = new WorldEntityRemovalBehaviour();

    private WorldEntityRemovalBehaviour() {
    }

    public static WorldEntityRemovalBehaviour getInstance() {
        return INSTANCE;
    }

    public void detachMounts(Entity entity) {
        if (entity.passenger != null) {
            entity.passenger.mount((Entity) null);
        }

        if (entity.vehicle != null) {
            entity.mount((Entity) null);
        }
    }

    public boolean markDeadAndWasPlayer(Entity entity) {
        entity.die();
        return entity instanceof EntityHuman;
    }

    public boolean isPlayer(Entity entity) {
        return entity instanceof EntityHuman;
    }

    public void addPlayer(List players, Entity entity) {
        if (entity instanceof EntityHuman) {
            players.add((EntityHuman) entity);
        }
    }

    public void removePlayer(List players, Entity entity) {
        if (entity instanceof EntityHuman) {
            players.remove((EntityHuman) entity);
        }
    }
}
