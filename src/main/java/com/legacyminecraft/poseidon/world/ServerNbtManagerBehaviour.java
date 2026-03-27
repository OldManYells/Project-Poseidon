package com.legacyminecraft.poseidon.world;


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
        File targetFolder = worldprovider instanceof WorldProviderHell
                ? new File(worldFolder, "DIM-1")
                : worldFolder;
        targetFolder.mkdirs();
        return new NoOpChunkLoader(targetFolder);
    }

    public void stampWorldVersion(WorldData worlddata, List list) {
        worlddata.a(19132);
    }

    public IChunkLoader createChunkLoader(File worldFolder, Object worldprovider) {
        File targetFolder = worldFolder;
        if (worldprovider != null && worldprovider.getClass().getSimpleName().contains("Hell")) {
            targetFolder = new File(worldFolder, "DIM-1");
        }
        targetFolder.mkdirs();
        return new NoOpChunkLoader(targetFolder);
    }

    public void stampWorldVersion(Object worlddata, List list) {
        if (worlddata == null) {
            return;
        }
        try {
            worlddata.getClass().getMethod("a", Integer.TYPE).invoke(worlddata, Integer.valueOf(19132));
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public void flushRegionCache() {
        // Deferred in the lean scaffold.
    }

    private static final class NoOpChunkLoader implements IChunkLoader {
        private final File worldFolder;

        private NoOpChunkLoader(File worldFolder) {
            this.worldFolder = worldFolder;
        }

        @Override
        public com.legacyminecraft.compat.bukkit.Chunk loadChunk(com.legacyminecraft.compat.bukkit.World world, int chunkX, int chunkZ) {
            return null;
        }

        @Override
        public void saveChunk(com.legacyminecraft.compat.bukkit.World world, com.legacyminecraft.compat.bukkit.Chunk chunk) {
        }

        @Override
        public void saveChunkNOP(com.legacyminecraft.compat.bukkit.World world, com.legacyminecraft.compat.bukkit.Chunk chunk) {
        }
    }
}
