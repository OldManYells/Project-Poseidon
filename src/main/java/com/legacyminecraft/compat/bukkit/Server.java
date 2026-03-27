package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Canonical compat server scaffold for event bridge calls.
 */
public class Server {
    public PluginManager getPluginManager() {
        return new PluginManager();
    }

    public boolean dispatchCommand(Player player, String command) {
        return false;
    }

    public boolean isCommandHidden(String commandName) {
        return false;
    }

    public World getWorld(UUID uniqueId) {
        return null;
    }

    public World getWorld(String worldName) {
        return null;
    }

    public List<World> getWorlds() {
        return new ArrayList<World>();
    }

    public Player getPlayer(String playerName) {
        return new OfflinePlayer(playerName);
    }

    public int getSpawnRadius() {
        return 16;
    }
}
