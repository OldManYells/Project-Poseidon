package com.legacyminecraft.poseidon.runtime;

import net.minecraft.server.MinecraftServer;

/**
 * Canonical launcher bridge for invoking MinecraftServer.run from legacy thread wrappers.
 */
public final class ServerRunInvocationService {
    private static final ServerRunInvocationService INSTANCE = new ServerRunInvocationService();

    private ServerRunInvocationService() {
    }

    public static ServerRunInvocationService getInstance() {
        return INSTANCE;
    }

    public void runServer(MinecraftServer server) {
        server.run();
    }
}
