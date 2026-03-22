package com.legacyminecraft.poseidon.world;

import net.minecraft.server.IDataManager;

/**
 * Canonical behaviour for world-server level-save flush hooks.
 */
public final class WorldServerSaveLevelBehaviour {
    private static final WorldServerSaveLevelBehaviour INSTANCE = new WorldServerSaveLevelBehaviour();

    private WorldServerSaveLevelBehaviour() {
    }

    public static WorldServerSaveLevelBehaviour getInstance() {
        return INSTANCE;
    }

    public void flushDataManager(IDataManager dataManager) {
        dataManager.e();
    }
}
