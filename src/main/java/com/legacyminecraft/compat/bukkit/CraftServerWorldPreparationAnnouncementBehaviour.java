package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer world-preparation announcement wrapper glue.
 */
public final class CraftServerWorldPreparationAnnouncementBehaviour {
    private static final CraftServerWorldPreparationAnnouncementBehaviour INSTANCE =
            new CraftServerWorldPreparationAnnouncementBehaviour();

    private CraftServerWorldPreparationAnnouncementBehaviour() {
    }

    public static CraftServerWorldPreparationAnnouncementBehaviour getInstance() {
        return INSTANCE;
    }

    public void announce(MinecraftServer console, WorldServer internal) {
        System.out.print("Preparing start region for level " + (console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
    }
}
