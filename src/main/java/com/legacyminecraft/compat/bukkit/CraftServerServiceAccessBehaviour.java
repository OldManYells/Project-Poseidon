package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer service accessor wrapper glue.
 */
public final class CraftServerServiceAccessBehaviour {
    private static final CraftServerServiceAccessBehaviour INSTANCE =
            new CraftServerServiceAccessBehaviour();

    private CraftServerServiceAccessBehaviour() {
    }

    public static CraftServerServiceAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public PluginManager getPluginManager(PluginManager pluginManager) {
        return pluginManager;
    }

    public BukkitScheduler getScheduler(BukkitScheduler scheduler) {
        return scheduler;
    }

    public ServicesManager getServicesManager(ServicesManager servicesManager) {
        return servicesManager;
    }
}
