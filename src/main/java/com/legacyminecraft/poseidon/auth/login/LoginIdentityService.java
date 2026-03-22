package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical identity helpers for login/session endpoint labeling.
 */
public final class LoginIdentityService {
    private static final LoginIdentityService INSTANCE = new LoginIdentityService();

    private LoginIdentityService() {
    }

    public static LoginIdentityService getInstance() {
        return INSTANCE;
    }

    public boolean isCrackedUsername(String username) {
        return username != null && username.startsWith(".");
    }

    public String describeConnection(String username, Object socketAddress) {
        return username != null ? username + " [" + socketAddress + "]" : String.valueOf(socketAddress);
    }
}
