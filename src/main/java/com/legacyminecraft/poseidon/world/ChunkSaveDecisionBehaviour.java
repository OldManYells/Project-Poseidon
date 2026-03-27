package com.legacyminecraft.poseidon.world;

/**
 * Canonical behaviour for chunk dirty/save decision policy.
 */
public final class ChunkSaveDecisionBehaviour {
    private static final ChunkSaveDecisionBehaviour INSTANCE = new ChunkSaveDecisionBehaviour();

    private ChunkSaveDecisionBehaviour() {
    }

    public static ChunkSaveDecisionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldSave(boolean neverSave, boolean hasEntities, long worldTime, long lastSavedTime,
                              boolean forceSave, boolean terrainDirty) {
        if (neverSave) {
            return false;
        }
        if (forceSave) {
            return hasEntities && worldTime != lastSavedTime || terrainDirty;
        }
        return hasEntities && worldTime >= lastSavedTime + 600L || terrainDirty;
    }
}

