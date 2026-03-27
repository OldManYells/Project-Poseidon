package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer reload property-manager wrapper glue.
 */
public final class CraftServerReloadPropertyManagerBehaviour {
    private static final CraftServerReloadPropertyManagerBehaviour INSTANCE =
            new CraftServerReloadPropertyManagerBehaviour();

    private CraftServerReloadPropertyManagerBehaviour() {
    }

    public static CraftServerReloadPropertyManagerBehaviour getInstance() {
        return INSTANCE;
    }

    public PropertyManager createAndApply(MinecraftServer console) {
        PropertyManager config = new PropertyManager(console.options);
        console.propertyManager = config;
        return config;
    }
}
