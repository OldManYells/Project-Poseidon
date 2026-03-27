package com.legacyminecraft.poseidon.runtime;

import joptsimple.OptionSet;

import java.util.logging.Logger;

/**
 * Role-aligned canonical system for server process entrypoint launch.
 */
public final class ServerEntryPointSystem {
    private static final ServerEntryPointSystem INSTANCE = new ServerEntryPointSystem();
    private final ServerEntryPointService delegate = ServerEntryPointService.getInstance();

    private ServerEntryPointSystem() {
    }

    public static ServerEntryPointSystem getInstance() {
        return INSTANCE;
    }

    public void launch(
            OptionSet options,
            Logger logger,
            ServerEntryPointService.ServerRunnableFactory serverRunnableFactory
    ) {
        delegate.launch(options, logger, serverRunnableFactory);
    }
}
