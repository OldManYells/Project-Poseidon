package com.legacyminecraft.poseidon.network;

import java.util.Random;

/**
 * Canonical handshake helpers for login sessions.
 */
public final class LoginHandshakeSystem {
    private static final LoginHandshakeSystem INSTANCE = new LoginHandshakeSystem();

    private LoginHandshakeSystem() {
    }

    public static LoginHandshakeSystem getInstance() {
        return INSTANCE;
    }

    public String createServerId(Random random) {
        return Long.toHexString(random.nextLong());
    }

    public String resolveHandshakeToken(boolean onlineMode, String generatedServerId) {
        return onlineMode ? generatedServerId : "-";
    }
}
