package com.legacyminecraft.poseidon.network;

/**
 * Canonical kick-message policy for movement speed violations.
 */
public final class MovementSpeedKickMessagePolicy {
    private static final MovementSpeedKickMessagePolicy INSTANCE = new MovementSpeedKickMessagePolicy();
    private static final String SPEED_VIOLATION_KICK_MESSAGE = "You moved too quickly :( (Hacking?)";

    private MovementSpeedKickMessagePolicy() {
    }

    public static MovementSpeedKickMessagePolicy getInstance() {
        return INSTANCE;
    }

    public String speedViolationKickMessage() {
        return SPEED_VIOLATION_KICK_MESSAGE;
    }
}
