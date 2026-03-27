package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftBlock identity accessors.
 */
public final class BlockIdentityAccessBehaviour {
    private static final BlockIdentityAccessBehaviour INSTANCE = new BlockIdentityAccessBehaviour();

    private BlockIdentityAccessBehaviour() {
    }

    public static BlockIdentityAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public World getWorld(CraftChunk chunk) {
        return chunk.getWorld();
    }

    public int getX(int x) {
        return x;
    }

    public int getY(int y) {
        return y;
    }

    public int getZ(int z) {
        return z;
    }

    public Chunk getChunk(CraftChunk chunk) {
        return chunk;
    }
}
