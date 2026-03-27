package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat item-entity wrapper scaffold.
 */
public class CraftItem extends Item {
    public CraftItem(CraftServer server, EntityItem item) {
        super(-1, "CRAFT_ITEM");
    }
}
