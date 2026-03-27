package com.legacyminecraft.compat.bukkit;

import jline.ConsoleReader;

import java.util.logging.Logger;

/**
 * Canonical behaviour for CraftServer runtime accessor wrapper glue.
 */
public final class CraftServerRuntimeAccessBehaviour {
    private static final CraftServerRuntimeAccessBehaviour INSTANCE = new CraftServerRuntimeAccessBehaviour();

    private CraftServerRuntimeAccessBehaviour() {
    }

    public static CraftServerRuntimeAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public int getMaxPlayers(ServerConfigurationManager configurationManager) {
        return configurationManager.maxPlayers;
    }

    public ServerConfigurationManager getHandle(ServerConfigurationManager configurationManager) {
        return configurationManager;
    }

    public MinecraftServer getServer(MinecraftServer console) {
        return console;
    }

    public Logger getLogger(Logger logger) {
        return logger;
    }

    public ConsoleReader getReader(MinecraftServer console) {
        return console.reader;
    }

    public void savePlayers(ServerConfigurationManager configurationManager) {
        configurationManager.savePlayers();
    }
}
