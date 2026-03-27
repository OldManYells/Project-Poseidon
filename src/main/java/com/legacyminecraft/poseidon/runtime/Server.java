package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.compat.bukkit.BukkitScheduler;
import com.legacyminecraft.compat.bukkit.ChunkGenerator;
import com.legacyminecraft.compat.bukkit.CraftScheduler;

/**
 * Runtime-local server facade.
 */
public class Server extends com.legacyminecraft.compat.bukkit.Server {
    private final BukkitScheduler scheduler = new CraftScheduler();
    private int spawnRadius = 16;

    @Override
    public boolean dispatchCommand(com.legacyminecraft.compat.bukkit.Player player, String command) {
        return false;
    }

    public BukkitScheduler getScheduler() {
        return scheduler;
    }

    public void disablePlugins() {
        getPluginManager().clearPlugins();
    }

    public void setSpawnRadius(int spawnRadius) {
        this.spawnRadius = spawnRadius;
    }

    @Override
    public int getSpawnRadius() {
        return spawnRadius;
    }

    public ChunkGenerator getGenerator(String worldName) {
        return null;
    }
}
