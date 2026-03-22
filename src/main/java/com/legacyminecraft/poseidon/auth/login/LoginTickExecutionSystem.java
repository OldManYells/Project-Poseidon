package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for applying login timeout/deferred-login tick decisions.
 */
public final class LoginTickExecutionSystem {
    private static final LoginTickExecutionSystem INSTANCE = new LoginTickExecutionSystem();

    private LoginTickExecutionSystem() {
    }

    public static LoginTickExecutionSystem getInstance() {
        return INSTANCE;
    }

    public int applyTickDecision(LoginTickPolicy.TickDecision tickDecision, TickActions tickActions) {
        if (tickDecision.shouldProcessDeferredLogin()) {
            tickActions.processDeferredLogin();
        }

        if (tickDecision.shouldDisconnectForTimeout()) {
            tickActions.disconnect(tickDecision.getTimeoutKickMessage());
        } else {
            tickActions.pollNetwork();
        }

        return tickDecision.getNextTimeoutCounter();
    }

    public interface TickActions {
        void processDeferredLogin();

        void disconnect(String message);

        void pollNetwork();
    }
}
