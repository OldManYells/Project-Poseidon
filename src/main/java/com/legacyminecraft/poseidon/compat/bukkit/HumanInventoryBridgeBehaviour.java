package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityHuman;
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;

/**
 * Canonical behaviour for CraftHumanEntity inventory-handle bridge creation.
 */
public final class HumanInventoryBridgeBehaviour {
    private static final HumanInventoryBridgeBehaviour INSTANCE = new HumanInventoryBridgeBehaviour();

    private HumanInventoryBridgeBehaviour() {
    }

    public static HumanInventoryBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftInventoryPlayer createInventory(EntityHuman entityHuman) {
        return new CraftInventoryPlayer(entityHuman.inventory);
    }
}
