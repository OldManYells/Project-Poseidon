package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Canonical compat MinecraftServer scaffold.
 */
public class MinecraftServer {
    public static final Logger log = Logger.getLogger("MinecraftServer");

    public final ServerConfigurationManager serverConfigurationManager = new ServerConfigurationManager();
    public final List<WorldServer> worlds = new ArrayList<WorldServer>();
    public final PropertyManager propertyManager = new PropertyManager();
    public final NetworkListenThread networkListenThread = new NetworkListenThread();
    public final Server server = new Server();

    public MinecraftServer() {
    }

    public MinecraftServer(Object options) {
    }

    public WorldServer getWorldServer(int dimension) {
        if (worlds.isEmpty()) {
            WorldServer worldServer = new WorldServer();
            worldServer.dimension = dimension;
            worlds.add(worldServer);
        }
        return worlds.get(0);
    }

    public void c(String message) {
        log.info(message);
    }

    public void a() {
    }

    public static void main(Object options) {
    }
}

