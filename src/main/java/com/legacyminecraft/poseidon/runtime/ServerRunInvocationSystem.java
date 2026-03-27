package com.legacyminecraft.poseidon.runtime;


/**
 * Role-aligned canonical facade for invoking the server run entrypoint.
 */
public final class ServerRunInvocationSystem {
    private static final ServerRunInvocationSystem INSTANCE = new ServerRunInvocationSystem();
    private final ServerRunInvocationService delegate = ServerRunInvocationService.getInstance();

    private ServerRunInvocationSystem() {
    }

    public static ServerRunInvocationSystem getInstance() {
        return INSTANCE;
    }

    public void runServer(MinecraftServer server) {
        delegate.runServer(server);
    }

    public void runServerRaw(Object server) {
        delegate.runServerRaw(server);
    }
}
