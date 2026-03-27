package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for detaching entities from chunk containers during removal.
 */
public final class WorldEntityChunkDetachBehaviour {
    private static final WorldEntityChunkDetachBehaviour INSTANCE = new WorldEntityChunkDetachBehaviour();

    private WorldEntityChunkDetachBehaviour() {
    }

    public static WorldEntityChunkDetachBehaviour getInstance() {
        return INSTANCE;
    }

    public void detachFromChunkIfPresent(World world, Entity entity, int chunkX, int chunkZ) {
        if (!entity.bG) {
            return;
        }

        if (!world.chunkProvider.isChunkLoaded(chunkX, chunkZ)) {
            return;
        }

        world.getChunkAt(chunkX, chunkZ).b(entity);
    }
}
