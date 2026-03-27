package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for login shutdown kick messaging.
 */
public final class LoginShutdownMessageConfigPolicy {
    private static final LoginShutdownMessageConfigPolicy INSTANCE = new LoginShutdownMessageConfigPolicy();
    private static final String KICK_SHUTDOWN_KEY = "message.kick.shutdown";

    private LoginShutdownMessageConfigPolicy() {
    }

    public static LoginShutdownMessageConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String kickShutdownKey() {
        return KICK_SHUTDOWN_KEY;
    }
}
