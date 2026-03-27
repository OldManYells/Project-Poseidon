package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld safe unload orchestration wrapper glue.
 */
public final class CraftWorldChunkSafeUnloadOrchestrationBehaviour {
    private static final CraftWorldChunkSafeUnloadOrchestrationBehaviour INSTANCE =
            new CraftWorldChunkSafeUnloadOrchestrationBehaviour();

    private static final CraftWorldChunkLifecycleBehaviour CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR =
            CraftWorldChunkLifecycleBehaviour.getInstance();
    private static final CraftWorldChunkUnloadBehaviour CRAFT_WORLD_CHUNK_UNLOAD_BEHAVIOUR =
            CraftWorldChunkUnloadBehaviour.getInstance();

    private CraftWorldChunkSafeUnloadOrchestrationBehaviour() {
    }

    public static CraftWorldChunkSafeUnloadOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean unloadChunkRequest(Player[] onlinePlayers, ChunkProviderServer chunkProviderServer, World world, int x, int z, boolean safe, int safeRadius) {
        if (!CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR.canUnloadChunk(
                onlinePlayers,
                world,
                x,
                z,
                safe,
                safeRadius
        )) {
            return false;
        }

        CRAFT_WORLD_CHUNK_UNLOAD_BEHAVIOUR.queueUnload(chunkProviderServer, x, z);
        return true;
    }

    public boolean unloadChunk(Player[] onlinePlayers, ChunkProviderServer chunkProviderServer, World world, int x, int z, boolean save, boolean safe, int safeRadius) {
        if (!CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR.canUnloadChunk(
                onlinePlayers,
                world,
                x,
                z,
                safe,
                safeRadius
        )) {
            return false;
        }

        return CRAFT_WORLD_CHUNK_UNLOAD_BEHAVIOUR.unloadChunk(chunkProviderServer, x, z, save);
    }
}
