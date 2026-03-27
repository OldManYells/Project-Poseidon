package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer reload cleanup wrapper glue.
 */
public final class CraftServerReloadCleanupBehaviour {
    private static final CraftServerReloadCleanupBehaviour INSTANCE =
            new CraftServerReloadCleanupBehaviour();

    private CraftServerReloadCleanupBehaviour() {
    }

    public static CraftServerReloadCleanupBehaviour getInstance() {
        return INSTANCE;
    }

    public void cleanup(PluginManager pluginManager, SimpleCommandMap commandMap) {
        pluginManager.clearPlugins();
        commandMap.clearCommands();
    }
}
