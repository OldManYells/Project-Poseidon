package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer reload orchestration wrapper glue.
 */
public final class CraftServerReloadOrchestrationBehaviour {
    public interface ReloadActions {
        void loadConfig();

        PropertyManager createAndApplyPropertyManager();

        void applySettings(PropertyManager config);

        void cleanup();

        void drainAndWarn();

        void bootstrap();
    }

    private static final CraftServerReloadOrchestrationBehaviour INSTANCE =
            new CraftServerReloadOrchestrationBehaviour();

    private CraftServerReloadOrchestrationBehaviour() {
    }

    public static CraftServerReloadOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public void reload(ReloadActions actions) {
        actions.loadConfig();
        PropertyManager config = actions.createAndApplyPropertyManager();
        actions.applySettings(config);
        actions.cleanup();
        actions.drainAndWarn();
        actions.bootstrap();
    }
}
