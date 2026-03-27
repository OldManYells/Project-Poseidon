package com.legacyminecraft.poseidon.world;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * World-local server alias consumed by world bootstrap systems.
 */
public class MinecraftServer {
    public static final Logger log = Logger.getLogger("MinecraftServer");

    public final ServerConfigurationManager serverConfigurationManager = new ServerConfigurationManager();
    public final List<WorldServer> worlds = new ArrayList<WorldServer>();
    public final com.legacyminecraft.poseidon.runtime.PropertyManager propertyManager =
            new com.legacyminecraft.poseidon.runtime.PropertyManager();
    public final Server server = new Server();
    public boolean spawnAnimals = true;
    public boolean isStopped = false;

    public static boolean isRunning(MinecraftServer server) {
        return server != null && !server.isStopped;
    }

    public EntityTracker getTracker(int dimension) {
        return serverConfigurationManager.getTracker(dimension);
    }
}
