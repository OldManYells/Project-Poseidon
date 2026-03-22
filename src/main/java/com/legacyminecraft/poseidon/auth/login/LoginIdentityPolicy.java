package com.legacyminecraft.poseidon.auth.login;

/**
 * Role-aligned canonical facade for login identity checks and labels.
 */
public final class LoginIdentityPolicy {
    private static final LoginIdentityPolicy INSTANCE = new LoginIdentityPolicy();
    private final LoginIdentityService delegate = LoginIdentityService.getInstance();

    private LoginIdentityPolicy() {
    }

    public static LoginIdentityPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isCrackedUsername(String username) {
        return delegate.isCrackedUsername(username);
    }

    public String describeConnection(String username, Object socketAddress) {
        return delegate.describeConnection(username, socketAddress);
    }
}
