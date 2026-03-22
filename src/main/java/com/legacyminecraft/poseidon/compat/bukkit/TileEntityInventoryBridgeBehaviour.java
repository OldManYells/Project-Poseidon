package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.IInventory;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

/**
 * Canonical behaviour for bridging tile-entity inventories into CraftInventory wrappers.
 */
public final class TileEntityInventoryBridgeBehaviour {
    private static final TileEntityInventoryBridgeBehaviour INSTANCE = new TileEntityInventoryBridgeBehaviour();

    private TileEntityInventoryBridgeBehaviour() {
    }

    public static TileEntityInventoryBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public Inventory createInventory(IInventory inventory) {
        return new CraftInventory(inventory);
    }
}
