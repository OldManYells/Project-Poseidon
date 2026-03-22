package com.legacyminecraft.poseidon.entity;

/**
 * Canonical interaction mode for Packet7UseEntity actions.
 */
public enum EntityInteractionMode {
    INTERACT(0),
    ATTACK(1),
    UNKNOWN(-1);

    private final int rawAction;

    EntityInteractionMode(int rawAction) {
        this.rawAction = rawAction;
    }

    public int getRawAction() {
        return rawAction;
    }

    public static EntityInteractionMode fromRawAction(int rawAction) {
        if (rawAction == INTERACT.rawAction) {
            return INTERACT;
        }
        if (rawAction == ATTACK.rawAction) {
            return ATTACK;
        }
        return UNKNOWN;
    }
}
