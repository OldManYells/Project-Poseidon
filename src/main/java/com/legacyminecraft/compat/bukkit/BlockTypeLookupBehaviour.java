package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block type and light lookups.
 */
public final class BlockTypeLookupBehaviour {
    private static final BlockTypeLookupBehaviour INSTANCE = new BlockTypeLookupBehaviour();

    private BlockTypeLookupBehaviour() {
    }

    public static BlockTypeLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public int getTypeId(Chunk chunk, int x, int y, int z) {
        return chunk.getTypeId(x & 0xF, y & 0x7F, z & 0xF);
    }

    public Material getType(Chunk chunk, int x, int y, int z) {
        return Material.getMaterial(getTypeId(chunk, x, y, z));
    }

    public byte getData(Chunk chunk, int x, int y, int z) {
        return (byte) chunk.getData(x & 0xF, y & 0x7F, z & 0xF);
    }

    public byte getLightLevel(World world, int x, int y, int z) {
        return (byte) world.getLightLevel(x, y, z);
    }
}
