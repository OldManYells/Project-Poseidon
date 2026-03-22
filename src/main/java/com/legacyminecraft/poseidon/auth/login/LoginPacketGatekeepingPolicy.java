package com.legacyminecraft.poseidon.auth.login;

import net.minecraft.server.Packet1Login;

/**
 * Role-aligned canonical facade for login-packet gatekeeping decisions.
 */
public final class LoginPacketGatekeepingPolicy {
    private static final LoginPacketGatekeepingPolicy INSTANCE = new LoginPacketGatekeepingPolicy();
    private final LoginPacketGatekeepingService delegate = LoginPacketGatekeepingService.getInstance();

    private LoginPacketGatekeepingPolicy() {
    }

    public static LoginPacketGatekeepingPolicy getInstance() {
        return INSTANCE;
    }

    public GatekeepingResult evaluate(Packet1Login packet1login, boolean receivedLoginPacket) {
        return GatekeepingResult.wrap(delegate.evaluate(packet1login, receivedLoginPacket));
    }

    public static final class GatekeepingResult {
        private final LoginPacketGatekeepingService.GatekeepingResult delegateResult;

        private GatekeepingResult(LoginPacketGatekeepingService.GatekeepingResult delegateResult) {
            this.delegateResult = delegateResult;
        }

        static GatekeepingResult wrap(LoginPacketGatekeepingService.GatekeepingResult delegateResult) {
            return new GatekeepingResult(delegateResult);
        }

        public static GatekeepingResult reject(String disconnectMessage) {
            return wrap(LoginPacketGatekeepingService.GatekeepingResult.reject(disconnectMessage));
        }

        public static GatekeepingResult accept(String username, String protocolKickMessage) {
            return wrap(LoginPacketGatekeepingService.GatekeepingResult.accept(username, protocolKickMessage));
        }

        public boolean isAccepted() {
            return delegateResult.isAccepted();
        }

        public String getUsername() {
            return delegateResult.getUsername();
        }

        public String getDisconnectMessage() {
            return delegateResult.getDisconnectMessage();
        }

        public String getProtocolKickMessage() {
            return delegateResult.getProtocolKickMessage();
        }
    }
}
