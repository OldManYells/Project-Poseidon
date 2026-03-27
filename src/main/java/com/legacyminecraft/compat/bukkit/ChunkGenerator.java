package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Canonical compat chunk-generator scaffold.
 */
public class ChunkGenerator {
    public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
        return new byte[0];
    }

    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<BlockPopulator>();
    }
}

