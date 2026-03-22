package com.legacyminecraft.poseidon.auth.login;

import net.minecraft.server.Packet1Login;

/**
 * Role-aligned canonical facade for per-tick login timeout policy.
 */
public final class LoginTickPolicy {
    private static final LoginTickPolicy INSTANCE = new LoginTickPolicy();
    private final LoginTickService delegate = LoginTickService.getInstance();

    private LoginTickPolicy() {
    }

    public static LoginTickPolicy getInstance() {
        return INSTANCE;
    }

    public TickDecision evaluateTick(Packet1Login deferredLoginPacket, int timeoutCounter, int timeoutTicks) {
        return TickDecision.wrap(delegate.evaluateTick(deferredLoginPacket, timeoutCounter, timeoutTicks));
    }

    public static final class TickDecision {
        private final LoginTickService.TickDecision delegateDecision;

        private TickDecision(LoginTickService.TickDecision delegateDecision) {
            this.delegateDecision = delegateDecision;
        }

        static TickDecision wrap(LoginTickService.TickDecision delegateDecision) {
            return new TickDecision(delegateDecision);
        }

        public boolean shouldProcessDeferredLogin() {
            return delegateDecision.shouldProcessDeferredLogin();
        }

        public boolean shouldDisconnectForTimeout() {
            return delegateDecision.shouldDisconnectForTimeout();
        }

        public String getTimeoutKickMessage() {
            return delegateDecision.getTimeoutKickMessage();
        }

        public int getNextTimeoutCounter() {
            return delegateDecision.getNextTimeoutCounter();
        }
    }
}
