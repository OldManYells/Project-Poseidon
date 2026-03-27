package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat arrow wrapper scaffold.
 */
public class CraftArrow extends CraftEntity implements Arrow {
    public CraftArrow(CraftServer server, EntityArrow handle) {
        super(handle);
    }
}
