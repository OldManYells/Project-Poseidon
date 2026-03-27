package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat egg wrapper scaffold.
 */
public class CraftEgg extends CraftEntity implements Egg {
    public CraftEgg(CraftServer server, EntityEgg handle) {
        super(handle);
    }
}
