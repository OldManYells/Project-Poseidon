package com.legacyminecraft.poseidon.world;

import net.minecraft.server.World;
import net.minecraft.server.WorldProvider;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 * Canonical behaviour for world spawn placement and top-block probing policies.
 */
public final class WorldSpawnPlacementBehaviour {
    private static final WorldSpawnPlacementBehaviour INSTANCE = new WorldSpawnPlacementBehaviour();

    private WorldSpawnPlacementBehaviour() {
    }

    public static WorldSpawnPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canSpawn(org.bukkit.World world, WorldProvider worldProvider, ChunkGenerator generator, int x, int z) {
        if (generator != null) {
            return generator.canSpawn(world, x, z);
        }

        return worldProvider.canSpawn(x, z);
    }

    public int findTopSolidBlockType(World world, int x, int z, int startY) {
        int y = startY;
        while (!world.isEmpty(x, y + 1, z)) {
            ++y;
        }

        return world.getTypeId(x, y, z);
    }

    public int nextSpawnCoordinate(int current, Random random) {
        return current + random.nextInt(64) - random.nextInt(64);
    }

    public boolean shouldFallbackToOrigin(int attempts, int maxAttempts) {
        return attempts > maxAttempts;
    }
}
