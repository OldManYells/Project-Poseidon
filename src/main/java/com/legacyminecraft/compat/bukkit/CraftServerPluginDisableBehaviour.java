package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer plugin-disable wrapper glue.
 */
public final class CraftServerPluginDisableBehaviour {
    private static final CraftServerPluginDisableBehaviour INSTANCE =
            new CraftServerPluginDisableBehaviour();

    private CraftServerPluginDisableBehaviour() {
    }

    public static CraftServerPluginDisableBehaviour getInstance() {
        return INSTANCE;
    }

    public void disablePlugins(PluginManager pluginManager) {
        pluginManager.disablePlugins();
    }
}
