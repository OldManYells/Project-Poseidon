package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat CraftBlock scaffold.
 */
public class CraftBlock extends Block {
    private final CraftChunk chunk;

    public CraftBlock() {
        this(new CraftChunk(), 0, 0, 0);
    }

    public CraftBlock(CraftChunk chunk, int x, int y, int z) {
        this.chunk = chunk == null ? new CraftChunk() : chunk;
        this.setWorld(this.chunk.getWorld());
    }

    public CraftChunk getChunk() {
        return chunk;
    }

    public Location getLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }
}
