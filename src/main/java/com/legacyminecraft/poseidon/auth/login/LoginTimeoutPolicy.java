package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical timeout policy for pending login sessions.
 */
public final class LoginTimeoutPolicy {
    private static final LoginTimeoutPolicy INSTANCE = new LoginTimeoutPolicy();

    private LoginTimeoutPolicy() {
    }

    public static LoginTimeoutPolicy getInstance() {
        return INSTANCE;
    }

    public boolean shouldDisconnectForTimeout(int elapsedTicks, int timeoutTicks) {
        return elapsedTicks >= timeoutTicks;
    }

    public String getTimeoutKickMessage() {
        return "Took too long to log in";
    }
}
