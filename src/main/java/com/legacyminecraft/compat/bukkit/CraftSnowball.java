package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat snowball wrapper scaffold.
 */
public class CraftSnowball extends CraftEntity implements Snowball {
    public CraftSnowball(CraftServer server, EntitySnowball handle) {
        super(handle);
    }
}
