package com.legacyminecraft.poseidon.network;

import org.bukkit.ChatColor;

/**
 * Canonical policy for packet-spam queue threshold enforcement.
 */
public final class PacketSpamGuardSystem {
    private static final PacketSpamGuardSystem INSTANCE = new PacketSpamGuardSystem();
    private static final String UNKNOWN_USERNAME = "Unknown";
    private static final String DEFAULT_DISCONNECT_KEY = "disconnect.spam";
    private static final String KICK_REASON = ChatColor.RED + "[Poseidon] You have been kicked for packet spamming.";

    private PacketSpamGuardSystem() {
    }

    public static PacketSpamGuardSystem getInstance() {
        return INSTANCE;
    }

    public SpamDecision evaluate(boolean spamDetectionEnabled, int queueSize, int threshold, String username, boolean playerConnection) {
        if (!spamDetectionEnabled || queueSize <= threshold) {
            return SpamDecision.noAction();
        }

        String resolvedUsername = username == null ? UNKNOWN_USERNAME : username;
        String logMessage = createKickLogMessage(resolvedUsername, queueSize, threshold);
        if (playerConnection) {
            return SpamDecision.kickPlayer(logMessage, KICK_REASON);
        }
        return SpamDecision.disconnectConnection(logMessage, DEFAULT_DISCONNECT_KEY);
    }

    public String createKickLogMessage(String username, int queueSize, int threshold) {
        return "[Poseidon] Player " + username + " has been kicked for packet spamming. The queue size was "
                + queueSize + " and the threshold was " + threshold + ".";
    }

    public static final class SpamDecision {
        private final boolean shouldKickPlayer;
        private final boolean shouldDisconnectConnection;
        private final String kickReason;
        private final String disconnectKey;
        private final String logMessage;

        private SpamDecision(
                boolean shouldKickPlayer,
                boolean shouldDisconnectConnection,
                String kickReason,
                String disconnectKey,
                String logMessage
        ) {
            this.shouldKickPlayer = shouldKickPlayer;
            this.shouldDisconnectConnection = shouldDisconnectConnection;
            this.kickReason = kickReason;
            this.disconnectKey = disconnectKey;
            this.logMessage = logMessage;
        }

        public static SpamDecision noAction() {
            return new SpamDecision(false, false, null, null, null);
        }

        public static SpamDecision kickPlayer(String logMessage, String kickReason) {
            return new SpamDecision(true, false, kickReason, null, logMessage);
        }

        public static SpamDecision disconnectConnection(String logMessage, String disconnectKey) {
            return new SpamDecision(false, true, null, disconnectKey, logMessage);
        }

        public boolean shouldKickPlayer() {
            return shouldKickPlayer;
        }

        public boolean shouldDisconnectConnection() {
            return shouldDisconnectConnection;
        }

        public String getKickReason() {
            return kickReason;
        }

        public String getDisconnectKey() {
            return disconnectKey;
        }

        public boolean shouldLog() {
            return logMessage != null;
        }

        public String getLogMessage() {
            return logMessage;
        }
    }
}
