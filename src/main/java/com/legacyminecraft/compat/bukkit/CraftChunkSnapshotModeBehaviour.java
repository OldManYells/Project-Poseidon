package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for CraftChunk snapshot default mode flags.
 */
public final class CraftChunkSnapshotModeBehaviour {
    private static final CraftChunkSnapshotModeBehaviour INSTANCE = new CraftChunkSnapshotModeBehaviour();

    private CraftChunkSnapshotModeBehaviour() {
    }

    public static CraftChunkSnapshotModeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean includeMaxBlockYByDefault() {
        return true;
    }

    public boolean includeBiomeByDefault() {
        return false;
    }

    public boolean includeBiomeClimateByDefault() {
        return false;
    }
}
