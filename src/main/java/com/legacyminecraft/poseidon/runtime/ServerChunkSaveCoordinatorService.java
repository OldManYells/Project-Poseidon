package com.legacyminecraft.poseidon.runtime;


import java.util.List;
import java.util.logging.Logger;

/**
 * Canonical chunk save coordination for legacy MinecraftServer wrappers.
 */
public final class ServerChunkSaveCoordinatorService {
    private static final ServerChunkSaveCoordinatorService INSTANCE = new ServerChunkSaveCoordinatorService();

    private final ServerWorldSaveService serverWorldSaveService = ServerWorldSaveService.getInstance();

    private ServerChunkSaveCoordinatorService() {
    }

    public static ServerChunkSaveCoordinatorService getInstance() {
        return INSTANCE;
    }

    public void saveChunks(List worlds, Server server, SavePlayersAction savePlayersAction, Logger logger) {
        logger.info("Saving chunks");
        serverWorldSaveService.saveWorldsAndEmitEvents(worlds, server);

        WorldServer world = (WorldServer) worlds.get(0);
        if (serverWorldSaveService.shouldSavePlayersAfterWorldSave(world.canSave)) {
            savePlayersAction.savePlayers();
        }
    }

    public interface SavePlayersAction {
        void savePlayers();
    }
}
