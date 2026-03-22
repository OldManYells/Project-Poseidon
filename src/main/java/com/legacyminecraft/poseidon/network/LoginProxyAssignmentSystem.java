package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.Packet1Login;

/**
 * Canonical service for applying proxy-support results to login session state.
 */
public final class LoginProxyAssignmentSystem {
    private static final LoginProxyAssignmentSystem INSTANCE = new LoginProxyAssignmentSystem();

    private LoginProxyAssignmentSystem() {
    }

    public static LoginProxyAssignmentSystem getInstance() {
        return INSTANCE;
    }

    public ProxyAssignment resolveProxy(NetLoginHandler loginHandler, Packet1Login loginPacket) {
        return map(LoginProxySupport.handleProxy(loginHandler, loginPacket));
    }

    public ProxyAssignment map(LoginProxySupport.ProxyHandlingResult result) {
        return new ProxyAssignment(
                result.isAccepted(),
                result.getConnectionType(),
                result.getRawConnectionType(),
                result.isUsingReleaseToBeta()
        );
    }

    public static final class ProxyAssignment {
        private final boolean accepted;
        private final ConnectionType connectionType;
        private final int rawConnectionType;
        private final boolean usingReleaseToBeta;

        private ProxyAssignment(boolean accepted, ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta) {
            this.accepted = accepted;
            this.connectionType = connectionType;
            this.rawConnectionType = rawConnectionType;
            this.usingReleaseToBeta = usingReleaseToBeta;
        }

        public boolean isAccepted() {
            return accepted;
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
