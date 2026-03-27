package com.legacyminecraft.poseidon.world.chunk;

import com.legacyminecraft.poseidon.nbt.NBTTagCompound;
import com.legacyminecraft.poseidon.world.Chunk;
import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical behaviour for chunk NBT layout validation and coordinate correction.
 */
public final class ChunkNbtLayoutBehaviour {
    private static final ChunkNbtLayoutBehaviour INSTANCE = new ChunkNbtLayoutBehaviour();

    private ChunkNbtLayoutBehaviour() {
    }

    public static ChunkNbtLayoutBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean hasLevelData(NBTTagCompound chunkRootTag) {
        return chunkRootTag.hasKey("Level");
    }

    public boolean hasBlockData(NBTTagCompound levelTag) {
        return levelTag.hasKey("Blocks");
    }

    public Chunk loadChunk(World world, NBTTagCompound levelTag) {
        return ChunkLoader.a(world, levelTag);
    }

    public boolean isExpectedChunkLocation(Chunk chunk, int expectedChunkX, int expectedChunkZ) {
        return chunk.a(expectedChunkX, expectedChunkZ);
    }

    public void overwriteChunkCoordinates(NBTTagCompound levelTag, int expectedChunkX, int expectedChunkZ) {
        levelTag.a("xPos", expectedChunkX);
        levelTag.a("zPos", expectedChunkZ);
    }
}
