package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.api.network.ConnectionType;

/**
 * Canonical execution flow for applying resolved proxy session state to login handlers.
 */
public final class LoginProxySessionApplySystem {
    private static final LoginProxySessionApplySystem INSTANCE = new LoginProxySessionApplySystem();

    private LoginProxySessionApplySystem() {
    }

    public static LoginProxySessionApplySystem getInstance() {
        return INSTANCE;
    }

    public boolean applyProxySessionState(
            LoginProxyAssignmentSystem.ProxyAssignment proxyAssignment,
            LoginSessionStateSystem loginSessionStateSystem,
            SessionStateSink sessionStateSink
    ) {
        LoginSessionStateSystem.ProxyState proxyState = loginSessionStateSystem.applyProxyAssignment(proxyAssignment);
        sessionStateSink.apply(
                proxyState.getConnectionType(),
                proxyState.getRawConnectionType(),
                proxyState.isUsingReleaseToBeta()
        );

        return proxyAssignment.isAccepted();
    }

    public interface SessionStateSink {
        void apply(ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta);
    }
}
