package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer create-world finalization orchestration wrapper glue.
 */
public final class CraftServerWorldFinalizationOrchestrationBehaviour {
    public interface FinalizationActions {
        boolean canComplete();

        void registerWorld();

        void registerPopulators();

        void dispatchWorldInit();

        void announcePreparation();

        void prepareSpawn();

        void dispatchWorldLoad();

        World getWorld();
    }

    private static final CraftServerWorldFinalizationOrchestrationBehaviour INSTANCE =
            new CraftServerWorldFinalizationOrchestrationBehaviour();

    private CraftServerWorldFinalizationOrchestrationBehaviour() {
    }

    public static CraftServerWorldFinalizationOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public World finalizeWorldCreation(FinalizationActions actions) {
        if (!actions.canComplete()) {
            return null;
        }

        actions.registerWorld();
        actions.registerPopulators();
        actions.dispatchWorldInit();
        actions.announcePreparation();
        actions.prepareSpawn();
        actions.dispatchWorldLoad();
        return actions.getWorld();
    }
}
