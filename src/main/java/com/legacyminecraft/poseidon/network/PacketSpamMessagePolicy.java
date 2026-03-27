package com.legacyminecraft.poseidon.network;


/**
 * Canonical message policy for packet-spam evaluation and kick messaging.
 */
public final class PacketSpamMessagePolicy {
    private static final PacketSpamMessagePolicy INSTANCE = new PacketSpamMessagePolicy();
    private static final String UNKNOWN_USERNAME = "Unknown";
    private static final String KICK_REASON = ChatColor.RED + "[Poseidon] You have been kicked for packet spamming.";

    private PacketSpamMessagePolicy() {
    }

    public static PacketSpamMessagePolicy getInstance() {
        return INSTANCE;
    }

    public String unknownUsername() {
        return UNKNOWN_USERNAME;
    }

    public String spamKickReason() {
        return KICK_REASON;
    }
}
