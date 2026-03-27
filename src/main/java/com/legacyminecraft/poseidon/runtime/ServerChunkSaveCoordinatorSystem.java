package com.legacyminecraft.poseidon.runtime;


import java.util.List;
import java.util.logging.Logger;

/**
 * Role-aligned canonical system for server chunk-save coordination.
 */
public final class ServerChunkSaveCoordinatorSystem {
    private static final ServerChunkSaveCoordinatorSystem INSTANCE = new ServerChunkSaveCoordinatorSystem();
    private final ServerChunkSaveCoordinatorService delegate = ServerChunkSaveCoordinatorService.getInstance();

    private ServerChunkSaveCoordinatorSystem() {
    }

    public static ServerChunkSaveCoordinatorSystem getInstance() {
        return INSTANCE;
    }

    public void saveChunks(final List worlds, Server server, final SavePlayersAction savePlayersAction, Logger logger) {
        delegate.saveChunks(worlds, server, new ServerChunkSaveCoordinatorService.SavePlayersAction() {
            @Override
            public void savePlayers() {
                savePlayersAction.savePlayers();
            }
        }, logger);
    }

    public interface SavePlayersAction {
        void savePlayers();
    }
}
