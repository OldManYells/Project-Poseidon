package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat human wrapper scaffold.
 */
public class CraftHumanEntity extends CraftLivingEntity {
    public CraftHumanEntity(CraftServer server, EntityHuman handle) {
        super();
        if (handle != null) {
            this.id = handle.id;
            this.world = handle.world;
        }
    }
}
