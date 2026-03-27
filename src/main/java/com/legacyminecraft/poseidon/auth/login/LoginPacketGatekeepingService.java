package com.legacyminecraft.poseidon.auth.login;


/**
 * Canonical gatekeeping logic for initial login packets before proxy/auth flow.
 */
public final class LoginPacketGatekeepingService {
    private static final LoginPacketGatekeepingService INSTANCE = new LoginPacketGatekeepingService();
    private static final String DUPLICATE_LOGIN_KICK_MESSAGE = "Multiple login packets received.";

    private LoginPacketGatekeepingService() {
    }

    public static LoginPacketGatekeepingService getInstance() {
        return INSTANCE;
    }

    public GatekeepingResult evaluate(Object packet1login, boolean receivedLoginPacket) {
        if (LoginPacketValidation.isDuplicateLoginPacket(receivedLoginPacket)) {
            return GatekeepingResult.reject(DUPLICATE_LOGIN_KICK_MESSAGE);
        }

        int protocolVersion = ((Number) Bridge.readField(packet1login, "a")).intValue();
        String username = String.valueOf(Bridge.readField(packet1login, "name"));
        String protocolKickMessage = LoginPacketValidation.getProtocolVersionKickMessage(protocolVersion);
        return GatekeepingResult.accept(username, protocolKickMessage);
    }

    public static final class GatekeepingResult {
        private final boolean accepted;
        private final String username;
        private final String disconnectMessage;
        private final String protocolKickMessage;

        private GatekeepingResult(
                boolean accepted,
                String username,
                String disconnectMessage,
                String protocolKickMessage
        ) {
            this.accepted = accepted;
            this.username = username;
            this.disconnectMessage = disconnectMessage;
            this.protocolKickMessage = protocolKickMessage;
        }

        public static GatekeepingResult reject(String disconnectMessage) {
            return new GatekeepingResult(false, null, disconnectMessage, null);
        }

        public static GatekeepingResult accept(String username, String protocolKickMessage) {
            return new GatekeepingResult(true, username, null, protocolKickMessage);
        }

        public boolean isAccepted() {
            return accepted;
        }

        public String getUsername() {
            return username;
        }

        public String getDisconnectMessage() {
            return disconnectMessage;
        }

        public String getProtocolKickMessage() {
            return protocolKickMessage;
        }
    }

    private static final class Bridge {
        private static Object readField(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
