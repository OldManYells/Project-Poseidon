package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.compat.bukkit.PluginManager;

/**
 * Entity-local server facade.
 */
public class Server {
    public PluginManager getPluginManager() {
        return new PluginManager();
    }
}
