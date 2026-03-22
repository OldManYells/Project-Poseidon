package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Chunk;
import net.minecraft.server.EnumSkyBlock;
import net.minecraft.server.IWorldAccess;
import net.minecraft.server.World;

import java.util.List;

/**
 * Canonical behaviour for direct world light-value write and listener notify flow.
 */
public final class WorldLightWriteBehaviour {
    private static final WorldLightWriteBehaviour INSTANCE = new WorldLightWriteBehaviour();

    private WorldLightWriteBehaviour() {
    }

    public static WorldLightWriteBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeLightValue(World world, List worldAccessListeners, EnumSkyBlock lightLayer, int x, int y, int z, int lightValue) {
        if (!this.isWithinWorldBounds(x, z) || !this.isWithinBuildHeight(y)) {
            return;
        }

        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        if (!world.chunkProvider.isChunkLoaded(chunkX, chunkZ)) {
            return;
        }

        Chunk chunk = world.getChunkAt(chunkX, chunkZ);
        chunk.a(lightLayer, x & 15, y, z & 15, lightValue);

        for (int listenerIndex = 0; listenerIndex < worldAccessListeners.size(); ++listenerIndex) {
            ((IWorldAccess) worldAccessListeners.get(listenerIndex)).a(x, y, z);
        }
    }

    private boolean isWithinWorldBounds(int x, int z) {
        return x >= -32000000 && z >= -32000000 && x < 32000000 && z <= 32000000;
    }

    private boolean isWithinBuildHeight(int y) {
        return y >= 0 && y < 128;
    }
}
