package com.legacyminecraft.poseidon.auth.login;


import java.util.logging.Logger;

/**
 * Role-aligned canonical facade for login connection lifecycle operations.
 */
public final class LoginConnectionLifecycleSystem {
    private static final LoginConnectionLifecycleSystem INSTANCE = new LoginConnectionLifecycleSystem();
    private final LoginConnectionLifecycleService delegate = LoginConnectionLifecycleService.getInstance();

    private LoginConnectionLifecycleSystem() {
    }

    public static LoginConnectionLifecycleSystem getInstance() {
        return INSTANCE;
    }

    public void disconnect(Object networkManager, Logger logger, String identity, String reason) {
        delegate.disconnect(networkManager, logger, identity, reason);
    }

    public void reportConnectionLost(Logger logger, String identity) {
        delegate.reportConnectionLost(logger, identity);
    }

    public String createDisconnectLogMessage(String identity, String reason) {
        return delegate.createDisconnectLogMessage(identity, reason);
    }

    public String createConnectionLostLogMessage(String identity) {
        return delegate.createConnectionLostLogMessage(identity);
    }

    public String getProtocolErrorMessage() {
        return delegate.getProtocolErrorMessage();
    }
}
