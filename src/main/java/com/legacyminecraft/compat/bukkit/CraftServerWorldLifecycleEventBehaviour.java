package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer world lifecycle event wrapper glue.
 */
public final class CraftServerWorldLifecycleEventBehaviour {
    private static final CraftServerWorldLifecycleEventBehaviour INSTANCE =
            new CraftServerWorldLifecycleEventBehaviour();

    private CraftServerWorldLifecycleEventBehaviour() {
    }

    public static CraftServerWorldLifecycleEventBehaviour getInstance() {
        return INSTANCE;
    }

    public void dispatchWorldInit(PluginManager pluginManager, WorldServer internal) {
        pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));
    }

    public void dispatchWorldLoad(PluginManager pluginManager, WorldServer internal) {
        pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
    }
}
