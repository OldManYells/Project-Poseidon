package com.legacyminecraft.poseidon.runtime;


import java.util.logging.Logger;

/**
 * Role-aligned canonical system for server stop orchestration.
 */
public final class ServerStopLifecycleSystem {
    private static final ServerStopLifecycleSystem INSTANCE = new ServerStopLifecycleSystem();
    private final ServerStopLifecycleService delegate = ServerStopLifecycleService.getInstance();

    private ServerStopLifecycleSystem() {
    }

    public static ServerStopLifecycleSystem getInstance() {
        return INSTANCE;
    }

    public void stopServer(MinecraftServer server, Logger logger, final SaveChunksAction saveChunksAction) {
        delegate.stopServer(server, logger, new ServerStopLifecycleService.SaveChunksAction() {
            @Override
            public void saveChunks() {
                saveChunksAction.saveChunks();
            }
        });
    }

    public interface SaveChunksAction {
        void saveChunks();
    }
}

