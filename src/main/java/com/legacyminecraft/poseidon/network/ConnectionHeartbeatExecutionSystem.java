package com.legacyminecraft.poseidon.network;


/**
 * Canonical execution flow for applying heartbeat decisions to a connection.
 */
public final class ConnectionHeartbeatExecutionSystem {
    private static final ConnectionHeartbeatExecutionSystem INSTANCE = new ConnectionHeartbeatExecutionSystem();

    private ConnectionHeartbeatExecutionSystem() {
    }

    public static ConnectionHeartbeatExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean applyHeartbeat(int serverTickCounter,
                                  int lastKeepAliveTick,
                                  int keepAliveThresholdTicks,
                                  boolean movementProcessingFlag,
                                  ConnectionHeartbeatSystem heartbeatSystem,
                                  HeartbeatActions heartbeatActions) {
        ConnectionHeartbeatSystem.HeartbeatDecision heartbeatDecision =
                heartbeatSystem.evaluate(serverTickCounter, lastKeepAliveTick, keepAliveThresholdTicks);

        boolean updatedMovementFlag = movementProcessingFlag;
        if (heartbeatDecision.shouldResetMovementProcessingFlag()) {
            updatedMovementFlag = false;
        }
        if (heartbeatDecision.shouldPollNetwork()) {
            heartbeatActions.pollNetwork();
        }
        if (heartbeatDecision.shouldSendKeepAlive()) {
            heartbeatActions.sendPacket(heartbeatSystem.createKeepAlivePacket());
        }
        return updatedMovementFlag;
    }

    public interface HeartbeatActions {
        void pollNetwork();

        void sendPacket(Object packet);
    }
}
