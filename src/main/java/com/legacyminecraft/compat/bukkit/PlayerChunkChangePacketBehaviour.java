package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftPlayer chunk-change packet validation and construction.
 */
public final class PlayerChunkChangePacketBehaviour {
    private static final PlayerChunkChangePacketBehaviour INSTANCE = new PlayerChunkChangePacketBehaviour();

    private PlayerChunkChangePacketBehaviour() {
    }

    public static PlayerChunkChangePacketBehaviour getInstance() {
        return INSTANCE;
    }

    public Packet51MapChunk createChunkChangePacket(Location location, int sizeX, int sizeY, int sizeZ,
                                                    byte[] data) {
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();
        int chunkX = blockX >> 4;
        int chunkZ = blockZ >> 4;

        if (sizeX <= 0 || sizeY <= 0 || sizeZ <= 0) {
            return null;
        }
        if ((blockX + sizeX - 1) >> 4 != chunkX
                || (blockZ + sizeZ - 1) >> 4 != chunkZ
                || blockY < 0
                || blockY + sizeY > 128) {
            return null;
        }
        if (data.length != (sizeX * sizeY * sizeZ * 5) / 2) {
            return null;
        }

        return new Packet51MapChunk(blockX, blockY, blockZ, sizeX, sizeY, sizeZ, data);
    }
}
