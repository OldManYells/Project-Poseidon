package com.legacyminecraft.compat.bukkit;


/**
 * Canonical system for CraftChunk snapshot orchestration.
 */
public final class CraftChunkSnapshotSystem {
    private static final CraftChunkSnapshotSystem INSTANCE = new CraftChunkSnapshotSystem();

    private static final ChunkSnapshotCaptureBehaviour CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR =
            ChunkSnapshotCaptureBehaviour.getInstance();
    private static final CraftChunkSnapshotCreationBehaviour CRAFT_CHUNK_SNAPSHOT_CREATION_BEHAVIOUR =
            CraftChunkSnapshotCreationBehaviour.getInstance();

    private CraftChunkSnapshotSystem() {
    }

    public static CraftChunkSnapshotSystem getInstance() {
        return INSTANCE;
    }

    public org.bukkit.ChunkSnapshot createSnapshot(
            Object craftChunk,
            boolean includeMaxBlockY,
            boolean includeBiome,
            boolean includeBiomeTempRain
    ) {
        net.minecraft.server.Chunk chunk = (net.minecraft.server.Chunk) invoke(craftChunk, "getHandle");
        int chunkX = intValue(invoke(craftChunk, "getX"));
        int chunkZ = intValue(invoke(craftChunk, "getZ"));
        ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData = CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR.captureFromChunk(
                chunk,
                chunkX,
                chunkZ,
                includeMaxBlockY,
                includeBiome,
                includeBiomeTempRain
        );
        return CRAFT_CHUNK_SNAPSHOT_CREATION_BEHAVIOUR.createSnapshot(
                chunkX,
                chunkZ,
                (org.bukkit.craftbukkit.CraftWorld) invoke(craftChunk, "getWorld"),
                snapshotData
        );
    }

    private Object invoke(Object target, String method) {
        if (target == null) {
            return null;
        }
        try {
            return target.getClass().getMethod(method).invoke(target);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private int intValue(Object value) {
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }
}
