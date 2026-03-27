package com.legacyminecraft.poseidon.block;

/**
 * Canonical piston move-reaction enum scaffold.
 */
public enum PistonMoveReaction {
    NORMAL(0),
    BLOCK(1),
    BREAK(2);

    private final int id;

    PistonMoveReaction(int id) {
        this.id = id;
    }

    public static PistonMoveReaction getById(int id) {
        for (PistonMoveReaction reaction : values()) {
            if (reaction.id == id) {
                return reaction;
            }
        }
        return NORMAL;
    }
}
