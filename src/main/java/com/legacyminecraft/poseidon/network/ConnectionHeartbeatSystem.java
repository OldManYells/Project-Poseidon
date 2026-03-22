package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet0KeepAlive;

/**
 * Canonical keep-alive heartbeat timing logic for active player connections.
 */
public final class ConnectionHeartbeatSystem {
    private static final ConnectionHeartbeatSystem INSTANCE = new ConnectionHeartbeatSystem();

    private ConnectionHeartbeatSystem() {
    }

    public static ConnectionHeartbeatSystem getInstance() {
        return INSTANCE;
    }

    public HeartbeatDecision evaluate(int serverTickCounter, int lastKeepAliveTick, int keepAliveThresholdTicks) {
        return new HeartbeatDecision(
                true,
                true,
                shouldSendKeepAlive(serverTickCounter, lastKeepAliveTick, keepAliveThresholdTicks)
        );
    }

    public boolean shouldSendKeepAlive(int serverTickCounter, int lastKeepAliveTick, int keepAliveThresholdTicks) {
        return serverTickCounter - lastKeepAliveTick > keepAliveThresholdTicks;
    }

    public Packet0KeepAlive createKeepAlivePacket() {
        return new Packet0KeepAlive();
    }

    public static final class HeartbeatDecision {
        private final boolean shouldResetMovementProcessingFlag;
        private final boolean shouldPollNetwork;
        private final boolean shouldSendKeepAlive;

        private HeartbeatDecision(
                boolean shouldResetMovementProcessingFlag,
                boolean shouldPollNetwork,
                boolean shouldSendKeepAlive
        ) {
            this.shouldResetMovementProcessingFlag = shouldResetMovementProcessingFlag;
            this.shouldPollNetwork = shouldPollNetwork;
            this.shouldSendKeepAlive = shouldSendKeepAlive;
        }

        public boolean shouldResetMovementProcessingFlag() {
            return shouldResetMovementProcessingFlag;
        }

        public boolean shouldPollNetwork() {
            return shouldPollNetwork;
        }

        public boolean shouldSendKeepAlive() {
            return shouldSendKeepAlive;
        }
    }
}
