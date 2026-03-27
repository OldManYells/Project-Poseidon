package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftChunkSnapshot object construction.
 */
public final class CraftChunkSnapshotFactoryBehaviour {
    private static final CraftChunkSnapshotFactoryBehaviour INSTANCE = new CraftChunkSnapshotFactoryBehaviour();

    private CraftChunkSnapshotFactoryBehaviour() {
    }

    public static CraftChunkSnapshotFactoryBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftChunkSnapshot createSnapshot(
            int x,
            int z,
            String worldName,
            long worldTime,
            byte[] chunkBuffer,
            byte[] heightMap,
            net.minecraft.server.BiomeBase[] biomeData,
            double[] biomeTemperature,
            double[] biomeRainfall
    ) {
        return new CraftChunkSnapshot(
                x,
                z,
                worldName,
                worldTime,
                chunkBuffer,
                heightMap,
                biomeData,
                biomeTemperature,
                biomeRainfall
        );
    }
}
