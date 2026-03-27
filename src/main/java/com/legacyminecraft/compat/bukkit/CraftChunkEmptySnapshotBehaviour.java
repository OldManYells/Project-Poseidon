package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftChunk empty snapshot capture orchestration.
 */
public final class CraftChunkEmptySnapshotBehaviour {
    private static final CraftChunkEmptySnapshotBehaviour INSTANCE = new CraftChunkEmptySnapshotBehaviour();

    private static final ChunkSnapshotCaptureBehaviour CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR =
            ChunkSnapshotCaptureBehaviour.getInstance();

    private CraftChunkEmptySnapshotBehaviour() {
    }

    public static CraftChunkEmptySnapshotBehaviour getInstance() {
        return INSTANCE;
    }

    public org.bukkit.ChunkSnapshot createEmptySnapshot(
            org.bukkit.craftbukkit.CraftWorld world,
            int chunkX,
            int chunkZ,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData = CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR.captureEmpty(
                world.getHandle().getWorldChunkManager(),
                chunkX,
                chunkZ,
                includeBiome,
                includeBiomeTempRain
        );
        return CraftChunkEmptySnapshotFactoryBehaviour.getInstance().createEmptyChunkSnapshot(
                chunkX,
                chunkZ,
                world,
                snapshotData
        );
    }
}
