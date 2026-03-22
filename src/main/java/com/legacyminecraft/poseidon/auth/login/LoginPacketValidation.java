package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical validation rules for legacy login packets.
 */
public final class LoginPacketValidation {
    private LoginPacketValidation() {
    }

    public static boolean isDuplicateLoginPacket(boolean alreadyReceivedLoginPacket) {
        return alreadyReceivedLoginPacket;
    }

    public static String getProtocolVersionKickMessage(int protocolVersion) {
        if (protocolVersion == 14) {
            return null;
        }
        if (protocolVersion > 14) {
            return "Outdated server! I'm still on Beta 1.7.3";
        }
        return "Outdated client! Please use Beta 1.7.3";
    }
}
