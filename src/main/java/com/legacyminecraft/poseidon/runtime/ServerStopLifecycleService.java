package com.legacyminecraft.poseidon.runtime;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldServer;

import java.util.logging.Logger;

/**
 * Canonical server stop flow for legacy MinecraftServer wrapper.
 */
public final class ServerStopLifecycleService {
    private static final ServerStopLifecycleService INSTANCE = new ServerStopLifecycleService();

    private ServerStopLifecycleService() {
    }

    public static ServerStopLifecycleService getInstance() {
        return INSTANCE;
    }

    public void stopServer(MinecraftServer server, Logger logger, SaveChunksAction saveChunksAction) {
        logger.info("Stopping server");
        ServerRuntimeLifecycle.getInstance().onServerStopping();

        if (server.server != null) {
            server.server.disablePlugins();
        }

        if (server.serverConfigurationManager != null) {
            server.serverConfigurationManager.savePlayers();
        }

        WorldServer worldserver = server.worlds.get(0);
        if (worldserver != null) {
            saveChunksAction.saveChunks();
        }

        ServerShutdownReporter.logSessionStatistics(logger);
    }

    public interface SaveChunksAction {
        void saveChunks();
    }
}
