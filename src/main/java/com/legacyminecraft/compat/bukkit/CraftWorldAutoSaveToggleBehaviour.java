package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld auto-save toggle semantics.
 */
public final class CraftWorldAutoSaveToggleBehaviour {
    private static final CraftWorldAutoSaveToggleBehaviour INSTANCE = new CraftWorldAutoSaveToggleBehaviour();

    private CraftWorldAutoSaveToggleBehaviour() {
    }

    public static CraftWorldAutoSaveToggleBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isAutoSave(WorldServer worldServer) {
        return !worldServer.canSave;
    }

    public void setAutoSave(WorldServer worldServer, boolean value) {
        worldServer.canSave = !value;
    }
}
