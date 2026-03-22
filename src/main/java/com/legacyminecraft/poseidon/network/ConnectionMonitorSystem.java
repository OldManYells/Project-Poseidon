package com.legacyminecraft.poseidon.network;

/**
 * Canonical close-monitor workflow for legacy connection watchdog threads.
 */
public final class ConnectionMonitorSystem {
    private static final ConnectionMonitorSystem INSTANCE = new ConnectionMonitorSystem();

    private ConnectionMonitorSystem() {
    }

    public static ConnectionMonitorSystem getInstance() {
        return INSTANCE;
    }

    public void monitorAndDisconnectIfOpen(ConnectionState connectionState, Runnable interruptWriter, Runnable disconnectAction) {
        try {
            Thread.sleep(2000L);
            if (connectionState.isConnectionOpen()) {
                interruptWriter.run();
                disconnectAction.run();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public interface ConnectionState {
        boolean isConnectionOpen();
    }
}
