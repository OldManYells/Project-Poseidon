package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.api.network.ConnectionType;

/**
 * Canonical state helpers for login session mutable flags/metadata.
 */
public final class LoginSessionStateSystem {
    private static final LoginSessionStateSystem INSTANCE = new LoginSessionStateSystem();

    private LoginSessionStateSystem() {
    }

    public static LoginSessionStateSystem getInstance() {
        return INSTANCE;
    }

    public boolean markReceivedKeepAlive(boolean currentValue) {
        return true;
    }

    public ProxyState applyProxyAssignment(LoginProxyAssignmentSystem.ProxyAssignment proxyAssignment) {
        return new ProxyState(
                proxyAssignment.getConnectionType(),
                proxyAssignment.getRawConnectionType(),
                proxyAssignment.isUsingReleaseToBeta()
        );
    }

    public static final class ProxyState {
        private final ConnectionType connectionType;
        private final int rawConnectionType;
        private final boolean usingReleaseToBeta;

        private ProxyState(ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta) {
            this.connectionType = connectionType;
            this.rawConnectionType = rawConnectionType;
            this.usingReleaseToBeta = usingReleaseToBeta;
        }

        public ConnectionType getConnectionType() {
            return connectionType;
        }

        public int getRawConnectionType() {
            return rawConnectionType;
        }

        public boolean isUsingReleaseToBeta() {
            return usingReleaseToBeta;
        }
    }
}
