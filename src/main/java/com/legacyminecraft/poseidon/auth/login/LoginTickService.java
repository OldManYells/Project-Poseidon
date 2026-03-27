package com.legacyminecraft.poseidon.auth.login;


/**
 * Canonical per-tick decision logic for login handlers.
 */
public final class LoginTickService {
    private static final LoginTickService INSTANCE = new LoginTickService();
    private final LoginTimeoutPolicy loginTimeoutPolicy = LoginTimeoutPolicy.getInstance();

    private LoginTickService() {
    }

    public static LoginTickService getInstance() {
        return INSTANCE;
    }

    public TickDecision evaluateTick(Object deferredLoginPacket, int timeoutCounter, int timeoutTicks) {
        int nextTimeoutCounter = timeoutCounter + 1;
        boolean shouldDisconnectForTimeout = loginTimeoutPolicy.shouldDisconnectForTimeout(timeoutCounter, timeoutTicks);
        String timeoutKickMessage = shouldDisconnectForTimeout ? loginTimeoutPolicy.getTimeoutKickMessage() : null;

        return new TickDecision(deferredLoginPacket != null, shouldDisconnectForTimeout, nextTimeoutCounter, timeoutKickMessage);
    }

    public static final class TickDecision {
        private final boolean shouldProcessDeferredLogin;
        private final boolean shouldDisconnectForTimeout;
        private final int nextTimeoutCounter;
        private final String timeoutKickMessage;

        private TickDecision(
                boolean shouldProcessDeferredLogin,
                boolean shouldDisconnectForTimeout,
                int nextTimeoutCounter,
                String timeoutKickMessage
        ) {
            this.shouldProcessDeferredLogin = shouldProcessDeferredLogin;
            this.shouldDisconnectForTimeout = shouldDisconnectForTimeout;
            this.nextTimeoutCounter = nextTimeoutCounter;
            this.timeoutKickMessage = timeoutKickMessage;
        }

        public boolean shouldProcessDeferredLogin() {
            return shouldProcessDeferredLogin;
        }

        public boolean shouldDisconnectForTimeout() {
            return shouldDisconnectForTimeout;
        }

        public int getNextTimeoutCounter() {
            return nextTimeoutCounter;
        }

        public String getTimeoutKickMessage() {
            return timeoutKickMessage;
        }
    }
}
