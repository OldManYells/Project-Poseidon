package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behaviour for CraftBukkit normal chunk-generator delegation.
 */
public final class NormalChunkGeneratorBehaviour {
    private static final NormalChunkGeneratorBehaviour INSTANCE = new NormalChunkGeneratorBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();

    private NormalChunkGeneratorBehaviour() {
    }

    public static NormalChunkGeneratorBehaviour getInstance() {
        return INSTANCE;
    }

    public IChunkProvider resolveChunkProvider(World world) {
        return world.worldProvider.getChunkProvider();
    }

    public byte[] generateUnsupported() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean canSpawn(com.legacyminecraft.compat.bukkit.World world, int x, int z) {
        return WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world).worldProvider.canSpawn(x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(com.legacyminecraft.compat.bukkit.World world) {
        return new ArrayList<BlockPopulator>();
    }

    public boolean isChunkLoaded(IChunkProvider provider, int chunkX, int chunkZ) {
        return provider.isChunkLoaded(chunkX, chunkZ);
    }

    public Chunk getOrCreateChunk(IChunkProvider provider, int chunkX, int chunkZ) {
        return provider.getOrCreateChunk(chunkX, chunkZ);
    }

    public Chunk getChunkAt(IChunkProvider provider, int chunkX, int chunkZ) {
        return provider.getChunkAt(chunkX, chunkZ);
    }

    public void getChunkAt(IChunkProvider provider, IChunkProvider requester, int chunkX, int chunkZ) {
        provider.getChunkAt(requester, chunkX, chunkZ);
    }

    public boolean saveChunks(IChunkProvider provider, boolean save, IProgressUpdate progressUpdate) {
        return provider.saveChunks(save, progressUpdate);
    }

    public boolean unloadChunks(IChunkProvider provider) {
        return provider.unloadChunks();
    }

    public boolean canSave(IChunkProvider provider) {
        return provider.canSave();
    }
}
