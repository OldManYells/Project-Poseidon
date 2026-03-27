package com.legacyminecraft.poseidon.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Canonical world-server scaffold for migrated movement and map behaviour.
 */
public class WorldServer extends World {
    public int dimension;
    public boolean canSave;
    public int spawnMonsters;
    public EntityTracker tracker = new EntityTracker();
    public final ChunkProviderServer chunkProviderServer = new ChunkProviderServer(this, null, null);
    public final List players = new ArrayList();
    private final ChunkCoordinates spawn = new ChunkCoordinates(0, 64, 0);
    private long seed;
    private long time;

    public WorldServer() {
    }

    public WorldServer(
            MinecraftServer server,
            ServerNBTManager serverNbtManager,
            String worldName,
            int dimension,
            long seed,
            com.legacyminecraft.compat.bukkit.Environment environment,
            ChunkGenerator chunkGenerator
    ) {
        this.dimension = dimension;
        this.seed = seed;
        this.time = 0L;
    }

    @Override
    public List getEntities(Entity entity, AxisAlignedBB bounds) {
        return Collections.emptyList();
    }

    public boolean b(AxisAlignedBB bounds) {
        return false;
    }

    public long getSeed() {
        return seed;
    }

    public long getTime() {
        return time;
    }

    public void setTimeAndFixTicklists(long worldTime) {
        this.time = worldTime;
    }

    public void setSpawnFlags(boolean spawnMonsters, boolean spawnAnimals) {
    }

    public void save(boolean forceSave, IProgressUpdate progressUpdate) {
    }

    public void saveLevel() {
    }

    public void addIWorldAccess(Object access) {
    }

    public void doTick() {
    }

    public ChunkCoordinates getSpawn() {
        return spawn;
    }

    public boolean doLighting() {
        return false;
    }

    public void cleanUp() {
    }

    @Override
    public com.legacyminecraft.compat.bukkit.CraftWorld getWorld() {
        return new com.legacyminecraft.compat.bukkit.CraftWorld();
    }
}
