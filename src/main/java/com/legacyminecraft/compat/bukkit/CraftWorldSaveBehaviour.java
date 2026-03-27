package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld save orchestration.
 */
public final class CraftWorldSaveBehaviour {
    private static final CraftWorldSaveBehaviour INSTANCE = new CraftWorldSaveBehaviour();

    private CraftWorldSaveBehaviour() {
    }

    public static CraftWorldSaveBehaviour getInstance() {
        return INSTANCE;
    }

    public void save(WorldServer worldServer) {
        boolean previousCanSave = worldServer.canSave;
        worldServer.canSave = false;
        worldServer.save(true, null);
        worldServer.canSave = previousCanSave;
    }
}
