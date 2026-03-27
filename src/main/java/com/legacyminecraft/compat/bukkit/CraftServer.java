package com.legacyminecraft.compat.bukkit;

import java.util.HashMap;
import java.util.Map;

/**
 * Canonical compat CraftServer scaffold.
 */
public class CraftServer extends Server {
    private final ServerConfigurationManager handle = new ServerConfigurationManager();
    private final MinecraftServer server = new MinecraftServer();
    private final Map<String, OfflinePlayer> offlinePlayers = new HashMap<String, OfflinePlayer>();

    public ServerConfigurationManager getHandle() {
        return handle;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public OfflinePlayer getOfflinePlayer(String playerName) {
        OfflinePlayer offlinePlayer = offlinePlayers.get(playerName);
        if (offlinePlayer == null) {
            offlinePlayer = new OfflinePlayer(playerName);
            offlinePlayers.put(playerName, offlinePlayer);
        }
        return offlinePlayer;
    }
}
