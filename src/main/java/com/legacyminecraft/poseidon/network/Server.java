package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.compat.bukkit.PluginManager;

/**
 * Network-local server facade.
 */
public class Server {
    public PluginManager getPluginManager() {
        return new PluginManager();
    }

    public boolean dispatchCommand(Player player, String command) {
        return false;
    }
}
