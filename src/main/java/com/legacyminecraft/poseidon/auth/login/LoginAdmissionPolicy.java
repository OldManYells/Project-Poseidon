package com.legacyminecraft.poseidon.auth.login;

import java.util.Set;

/**
 * Canonical login admission checks for ban-list, whitelist, and capacity decisions.
 */
public final class LoginAdmissionPolicy {
    private static final LoginAdmissionPolicy INSTANCE = new LoginAdmissionPolicy();

    private LoginAdmissionPolicy() {
    }

    public static LoginAdmissionPolicy getInstance() {
        return INSTANCE;
    }

    public AdmissionResult evaluate(
            String username,
            String ipAddress,
            Set<?> bannedUsernames,
            Set<?> bannedIps,
            boolean whitelisted,
            int onlinePlayers,
            int maxPlayers,
            String msgKickBanned,
            String msgKickIPBanned,
            String msgKickWhitelist,
            String msgKickServerFull) {
        String normalizedUsername = normalize(username);
        String normalizedIp = normalize(ipAddress);

        if (bannedUsernames.contains(normalizedUsername)) {
            return new AdmissionResult(Decision.KICK_BANNED, msgKickBanned);
        }
        if (bannedIps.contains(normalizedIp)) {
            return new AdmissionResult(Decision.KICK_BANNED_IP, msgKickIPBanned);
        }
        if (!whitelisted) {
            return new AdmissionResult(Decision.KICK_WHITELIST, msgKickWhitelist);
        }
        if (onlinePlayers >= maxPlayers) {
            return new AdmissionResult(Decision.KICK_FULL, msgKickServerFull);
        }
        return new AdmissionResult(Decision.ALLOWED, ipAddress);
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase();
    }

    public enum Decision {
        ALLOWED,
        KICK_BANNED,
        KICK_BANNED_IP,
        KICK_WHITELIST,
        KICK_FULL
    }

    public static final class AdmissionResult {
        private final Decision decision;
        private final String kickMessage;

        private AdmissionResult(Decision decision, String kickMessage) {
            this.decision = decision;
            this.kickMessage = kickMessage;
        }

        public Decision getDecision() {
            return decision;
        }

        public String getKickMessage() {
            return kickMessage;
        }
    }
}
