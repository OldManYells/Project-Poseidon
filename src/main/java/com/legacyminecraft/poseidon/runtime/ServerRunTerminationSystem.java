package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical termination flow for MinecraftServer.run() finalization.
 */
public final class ServerRunTerminationSystem {
    private static final ServerRunTerminationSystem INSTANCE = new ServerRunTerminationSystem();

    private ServerRunTerminationSystem() {
    }

    public static ServerRunTerminationSystem getInstance() {
        return INSTANCE;
    }

    public void executeTermination(TerminationActions terminationActions) {
        try {
            terminationActions.stopServer();
            terminationActions.markServerStopped();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            terminationActions.exitProcess(0);
        }
    }

    public interface TerminationActions {
        void stopServer();

        void markServerStopped();

        void exitProcess(int statusCode);
    }
}
