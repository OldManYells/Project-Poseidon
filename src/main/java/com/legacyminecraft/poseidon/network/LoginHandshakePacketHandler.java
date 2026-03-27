package com.legacyminecraft.poseidon.network;


import java.util.Random;

/**
 * Canonical handler for server handshake packet response composition.
 */
public final class LoginHandshakePacketHandler {
    private static final LoginHandshakePacketHandler INSTANCE = new LoginHandshakePacketHandler();
    private final LoginHandshakeSystem loginHandshakeSystem = LoginHandshakeSystem.getInstance();

    private LoginHandshakePacketHandler() {
    }

    public static LoginHandshakePacketHandler getInstance() {
        return INSTANCE;
    }

    public HandshakeDecision createHandshakeDecision(boolean onlineMode, String existingServerId, Random random) {
        String serverId = existingServerId;
        if (onlineMode) {
            serverId = loginHandshakeSystem.createServerId(random);
        }

        String handshakeToken = loginHandshakeSystem.resolveHandshakeToken(onlineMode, serverId);
        return new HandshakeDecision(serverId, NetworkCompatGatewayRegistry.gateway().createHandshakePacket(handshakeToken));
    }

    public static final class HandshakeDecision {
        private final String serverId;
        private final Object responsePacket;

        private HandshakeDecision(String serverId, Object responsePacket) {
            this.serverId = serverId;
            this.responsePacket = responsePacket;
        }

        public String getServerId() {
            return serverId;
        }

        public Object getResponsePacket() {
            return responsePacket;
        }
    }

}
