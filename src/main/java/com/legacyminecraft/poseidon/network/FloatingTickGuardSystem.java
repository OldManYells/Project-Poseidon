package com.legacyminecraft.poseidon.network;

/**
 * Canonical guard for floating tick accumulation and kick thresholds.
 */
public final class FloatingTickGuardSystem {
    private static final FloatingTickGuardSystem INSTANCE = new FloatingTickGuardSystem();
    private static final String DEFAULT_DISCONNECT_REASON = "Flying is not enabled on this server";
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();

    private FloatingTickGuardSystem() {
    }

    public static FloatingTickGuardSystem getInstance() {
        return INSTANCE;
    }

    public FloatingDecision evaluate(
            boolean allowFlight,
            boolean touchingGroundBelow,
            double verticalDelta,
            int currentFloatingTicks
    ) {
        if (!movementPacketPolicy.shouldIncrementFloatingCounter(allowFlight, touchingGroundBelow, verticalDelta)) {
            return FloatingDecision.reset();
        }

        int updatedFloatingTicks = currentFloatingTicks + 1;
        if (movementPacketPolicy.shouldKickForFloatingTicks(updatedFloatingTicks)) {
            return FloatingDecision.kick(updatedFloatingTicks, DEFAULT_DISCONNECT_REASON);
        }
        return FloatingDecision.increment(updatedFloatingTicks);
    }

    public String createFloatingKickLogMessage(String playerName) {
        return playerName + " was kicked for floating too long!";
    }

    public static final class FloatingDecision {
        private final int updatedFloatingTicks;
        private final boolean shouldKick;
        private final String disconnectReason;

        private FloatingDecision(int updatedFloatingTicks, boolean shouldKick, String disconnectReason) {
            this.updatedFloatingTicks = updatedFloatingTicks;
            this.shouldKick = shouldKick;
            this.disconnectReason = disconnectReason;
        }

        public static FloatingDecision reset() {
            return new FloatingDecision(0, false, null);
        }

        public static FloatingDecision increment(int updatedFloatingTicks) {
            return new FloatingDecision(updatedFloatingTicks, false, null);
        }

        public static FloatingDecision kick(int updatedFloatingTicks, String disconnectReason) {
            return new FloatingDecision(updatedFloatingTicks, true, disconnectReason);
        }

        public int getUpdatedFloatingTicks() {
            return updatedFloatingTicks;
        }

        public boolean shouldKick() {
            return shouldKick;
        }

        public String getDisconnectReason() {
            return disconnectReason;
        }
    }
}
