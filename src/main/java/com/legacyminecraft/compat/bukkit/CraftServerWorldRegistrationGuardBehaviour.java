package com.legacyminecraft.compat.bukkit;


import java.util.Map;

/**
 * Canonical behaviour for CraftServer world-registration guard wrapper glue.
 */
public final class CraftServerWorldRegistrationGuardBehaviour {
    private static final CraftServerWorldRegistrationGuardBehaviour INSTANCE =
            new CraftServerWorldRegistrationGuardBehaviour();

    private CraftServerWorldRegistrationGuardBehaviour() {
    }

    public static CraftServerWorldRegistrationGuardBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canCompleteCreateWorld(Map<String, World> worlds, String name) {
        return worlds.containsKey(name.toLowerCase());
    }
}
