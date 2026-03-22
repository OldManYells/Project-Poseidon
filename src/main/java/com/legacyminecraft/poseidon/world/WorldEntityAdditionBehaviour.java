package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Entity;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;

import java.util.List;

/**
 * Canonical behaviour for world entity-addition admission and insertion policy.
 */
public final class WorldEntityAdditionBehaviour {
    private static final WorldEntityAdditionBehaviour INSTANCE = new WorldEntityAdditionBehaviour();

    private WorldEntityAdditionBehaviour() {
    }

    public static WorldEntityAdditionBehaviour getInstance() {
        return INSTANCE;
    }

    public int entityChunkCoordinate(double locationCoordinate) {
        return MathHelper.floor(locationCoordinate / 16.0D);
    }

    public boolean shouldRejectBecauseChunkNotLoaded(boolean isPlayer, boolean isChunkLoaded) {
        return !isPlayer && !isChunkLoaded;
    }

    public void addEntityToChunkAndList(World world, List entityList, Entity entity, int chunkX, int chunkZ) {
        world.getChunkAt(chunkX, chunkZ).a(entity);
        entityList.add(entity);
    }
}
