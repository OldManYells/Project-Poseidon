package com.legacyminecraft.poseidon.runtime;

import joptsimple.OptionSet;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical entrypoint launcher for legacy MinecraftServer.main wrapper.
 */
public final class ServerEntryPointService {
    public interface ServerRunnableFactory {
        Runnable create(OptionSet options) throws Exception;
    }

    private static final ServerEntryPointService INSTANCE = new ServerEntryPointService();

    private ServerEntryPointService() {
    }

    public static ServerEntryPointService getInstance() {
        return INSTANCE;
    }

    public void launch(OptionSet options, Logger logger, ServerRunnableFactory serverRunnableFactory) {
        StatisticList.a();

        try {
            Runnable serverRunnable = serverRunnableFactory.create(options);
            Thread serverThread = new Thread(serverRunnable, "Server thread");
            serverThread.start();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Failed to start the minecraft server", exception);
        }
    }
}
