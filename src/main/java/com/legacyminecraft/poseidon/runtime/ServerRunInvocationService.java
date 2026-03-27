package com.legacyminecraft.poseidon.runtime;


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

    public void runServerRaw(Object server) {
        if (server == null) {
            return;
        }
        try {
            server.getClass().getMethod("run").invoke(server);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to invoke server run() entrypoint", e);
        }
    }
}
