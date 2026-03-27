package com.legacyminecraft.poseidon.network;

/**
 * Canonical disconnect reason policy for floating kick enforcement.
 */
public final class FloatingKickMessagePolicy {
    private static final FloatingKickMessagePolicy INSTANCE = new FloatingKickMessagePolicy();
    private static final String FLOATING_KICK_REASON = "Flying is not enabled on this server";

    private FloatingKickMessagePolicy() {
    }

    public static FloatingKickMessagePolicy getInstance() {
        return INSTANCE;
    }

    public String floatingKickReason() {
        return FLOATING_KICK_REASON;
    }
}
