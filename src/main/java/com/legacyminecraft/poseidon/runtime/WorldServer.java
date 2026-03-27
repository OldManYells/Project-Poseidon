package com.legacyminecraft.poseidon.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Runtime-local world facade for tick orchestration.
 */
public class WorldServer {
    public int dimension;
    public boolean canSave;
    public final List<EntityPlayer> players = new ArrayList<EntityPlayer>();
    public final EntityTracker tracker = new EntityTracker();
    private long time;
    private long seed;

    public void doTick() {
    }

    public boolean doLighting() {
        return false;
    }

    public void cleanUp() {
    }

    public void save(boolean forceSave, IProgressUpdate progressUpdate) {
    }

    public void saveLevel() {
    }

    public com.legacyminecraft.compat.bukkit.World getWorld() {
        return new com.legacyminecraft.compat.bukkit.World();
    }

    public void setSpawnFlags(boolean spawnMonsters, boolean spawnAnimals) {
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
}
