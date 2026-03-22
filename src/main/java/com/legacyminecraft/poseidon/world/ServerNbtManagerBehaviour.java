package com.legacyminecraft.poseidon.world;

import net.minecraft.server.ChunkRegionLoader;
import net.minecraft.server.IChunkLoader;
import net.minecraft.server.RegionFileCache;
import net.minecraft.server.WorldData;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldProviderHell;

import java.io.File;
import java.util.List;

public final class ServerNbtManagerBehaviour {
    private static final ServerNbtManagerBehaviour INSTANCE = new ServerNbtManagerBehaviour();

    private ServerNbtManagerBehaviour() {
    }

    public static ServerNbtManagerBehaviour getInstance() {
        return INSTANCE;
    }

    public IChunkLoader createChunkLoader(File worldFolder, WorldProvider worldprovider) {
        if (worldprovider instanceof WorldProviderHell) {
            File netherFolder = new File(worldFolder, "DIM-1");
            netherFolder.mkdirs();
            return new ChunkRegionLoader(netherFolder);
        }

        return new ChunkRegionLoader(worldFolder);
    }

    public void stampWorldVersion(WorldData worlddata, List list) {
        worlddata.a(19132);
    }

    public void flushRegionCache() {
        RegionFileCache.a();
    }
}
