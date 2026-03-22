package com.legacyminecraft.poseidon.block;

/**
 * Canonical redstone/instrument/pitch policy for legacy note-block wrappers.
 */
public final class NoteBlockStateBehaviour {
    private static final NoteBlockStateBehaviour INSTANCE = new NoteBlockStateBehaviour();

    private NoteBlockStateBehaviour() {
    }

    public static NoteBlockStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldHandleNeighborPowerUpdate(int neighborTypeId, boolean neighborIsPowerSource) {
        return neighborTypeId > 0 && neighborIsPowerSource;
    }

    public boolean hasPowerStateChanged(boolean previousPowered, boolean currentPowered) {
        return previousPowered != currentPowered;
    }

    public boolean shouldPlayOnPowerChange(boolean currentPowered) {
        return currentPowered;
    }

    public boolean shouldIgnoreClientInteraction(boolean worldIsStatic) {
        return worldIsStatic;
    }

    public float resolvePitchFromNoteValue(int noteValue) {
        return (float) Math.pow(2.0D, (double) (noteValue - 12) / 12.0D);
    }

    public String resolveInstrumentName(int instrumentType) {
        if (instrumentType == 1) {
            return "bd";
        }
        if (instrumentType == 2) {
            return "snare";
        }
        if (instrumentType == 3) {
            return "hat";
        }
        if (instrumentType == 4) {
            return "bassattack";
        }
        return "harp";
    }

    public String resolveSoundEffectName(String instrumentName) {
        return "note." + instrumentName;
    }

    public double resolveCenteredCoordinate(int coordinate) {
        return (double) coordinate + 0.5D;
    }

    public double resolveNoteParticleY(int y) {
        return (double) y + 1.2D;
    }

    public double resolveNoteParticleData(int noteValue) {
        return (double) noteValue / 24.0D;
    }
}
