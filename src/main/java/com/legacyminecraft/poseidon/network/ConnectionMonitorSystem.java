package com.legacyminecraft.poseidon.network;

/**
 * Canonical close-monitor workflow for legacy connection watchdog threads.
 */
public final class ConnectionMonitorSystem {
    private static final ConnectionMonitorSystem INSTANCE = new ConnectionMonitorSystem();
    private final ConnectionMonitorDelayPolicy connectionMonitorDelayPolicy = ConnectionMonitorDelayPolicy.getInstance();

    private ConnectionMonitorSystem() {
    }

    public static ConnectionMonitorSystem getInstance() {
        return INSTANCE;
    }

    public void monitorAndDisconnectIfOpen(ConnectionState connectionState, Runnable interruptWriter, Runnable disconnectAction) {
        try {
            Thread.sleep(connectionMonitorDelayPolicy.watchdogDelayMillis());
            if (connectionState.isConnectionOpen()) {
                interruptWriter.run();
                disconnectAction.run();
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        } catch (Exception ignored) {
            // Preserve legacy "best effort" semantics without spamming shutdown noise.
        }
    }

    public interface ConnectionState {
        boolean isConnectionOpen();
    }
}
