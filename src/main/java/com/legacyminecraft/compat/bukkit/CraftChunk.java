package com.legacyminecraft.compat.bukkit;

/**
 * Canonical CraftChunk scaffold.
 */
public class CraftChunk extends Chunk {
    public CraftChunk() {
        super();
    }

    public CraftChunk(Chunk chunk) {
        this.x = chunk == null ? 0 : chunk.x;
        this.z = chunk == null ? 0 : chunk.z;
    }

    public static ChunkSnapshot getEmptyChunkSnapshot(
            int x,
            int z,
            CraftWorld world,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        return new CraftChunkEmptySnapshot(x, z, world.getName(), world.getFullTime(), null, null, null);
    }

    public static ChunkSnapshot createEmptyChunkSnapshot(
            int x,
            int z,
            CraftWorld world,
            ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData
    ) {
        return new CraftChunkEmptySnapshot(
                x,
                z,
                world.getName(),
                world.getFullTime(),
                snapshotData == null ? null : snapshotData.getBiomes(),
                snapshotData == null ? null : snapshotData.getTemperatures(),
                snapshotData == null ? null : snapshotData.getRainfall()
        );
    }
}
