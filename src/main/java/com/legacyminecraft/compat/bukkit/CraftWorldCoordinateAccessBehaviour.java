package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld core coordinate accessor wrapper glue.
 */
public final class CraftWorldCoordinateAccessBehaviour {
    private static final CraftWorldCoordinateAccessBehaviour INSTANCE = new CraftWorldCoordinateAccessBehaviour();

    private CraftWorldCoordinateAccessBehaviour() {
    }

    public static CraftWorldCoordinateAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public Block getBlockAt(CraftWorld craftWorld, int x, int y, int z) {
        return craftWorld.getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0x7F, z & 0xF);
    }

    public int getBlockTypeIdAt(WorldServer worldServer, int x, int y, int z) {
        return worldServer.getTypeId(x, y, z);
    }

    public int getHighestBlockYAt(WorldServer worldServer, int x, int z) {
        return worldServer.getHighestBlockYAt(x, z);
    }

    public Chunk getChunkAt(WorldServer worldServer, int x, int z) {
        return worldServer.chunkProviderServer.getChunkAt(x, z).bukkitChunk;
    }

    public Chunk getChunkAt(CraftWorld craftWorld, Block block) {
        return craftWorld.getChunkAt(block.getX() >> 4, block.getZ() >> 4);
    }
}
