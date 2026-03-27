package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for chunk sky-light neighbor column update orchestration.
 */
public final class ChunkSkyLightColumnUpdateBehaviour {
    private static final ChunkSkyLightColumnUpdateBehaviour INSTANCE = new ChunkSkyLightColumnUpdateBehaviour();

    private ChunkSkyLightColumnUpdateBehaviour() {
    }

    public static ChunkSkyLightColumnUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public void relightNeighborColumns(World world, int chunkX, int chunkZ, int localX, int localZ, int localHeight,
                                       SkyLightUpdateActions actions) {
        int worldX = chunkX * 16 + localX;
        int worldZ = chunkZ * 16 + localZ;
        updateColumn(world, worldX - 1, worldZ, localHeight, actions);
        updateColumn(world, worldX + 1, worldZ, localHeight, actions);
        updateColumn(world, worldX, worldZ - 1, localHeight, actions);
        updateColumn(world, worldX, worldZ + 1, localHeight, actions);
    }

    public void updateColumn(World world, int worldX, int worldZ, int localHeight,
                             SkyLightUpdateActions actions) {
        int worldHeight = world.getHighestBlockYAt(worldX, worldZ);
        if (worldHeight > localHeight) {
            world.a(EnumSkyBlock.SKY, worldX, localHeight, worldZ, worldX, worldHeight, worldZ);
            actions.markDirty();
        } else if (worldHeight < localHeight) {
            world.a(EnumSkyBlock.SKY, worldX, worldHeight, worldZ, worldX, localHeight, worldZ);
            actions.markDirty();
        }
    }

    public interface SkyLightUpdateActions {
        void markDirty();
    }
}
