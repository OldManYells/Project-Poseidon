package com.legacyminecraft.poseidon.world.chunk;

import com.legacyminecraft.poseidon.nbt.NBTTagCompound;
import com.legacyminecraft.poseidon.world.Chunk;
import com.legacyminecraft.poseidon.world.World;

/**
 * World-chunk local NBT loader scaffold.
 */
public final class ChunkLoader {
    private ChunkLoader() {
    }

    public static Chunk a(World world, NBTTagCompound levelTag) {
        return new Chunk(world);
    }
}
