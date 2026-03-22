package com.legacyminecraft.poseidon.block;

/**
 * Canonical behaviour for piston power transition decisioning.
 */
public final class PistonPowerTransitionBehaviour {
    private static final PistonPowerTransitionBehaviour INSTANCE = new PistonPowerTransitionBehaviour();

    private PistonPowerTransitionBehaviour() {
    }

    public static PistonPowerTransitionBehaviour getInstance() {
        return INSTANCE;
    }

    public Transition resolveTransition(int blockData, boolean indirectlyPowered, boolean currentlyExtended) {
        if (blockData == 7) {
            return Transition.NONE;
        }
        if (indirectlyPowered && !currentlyExtended) {
            return Transition.EXTEND;
        }
        if (!indirectlyPowered && currentlyExtended) {
            return Transition.RETRACT;
        }
        return Transition.NONE;
    }

    public int composeExtendedData(int facing) {
        return facing | 8;
    }

    public int composeRetractedData(int facing) {
        return facing;
    }

    public enum Transition {
        NONE,
        EXTEND,
        RETRACT
    }
}
