package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.TileEntityFurnace;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

/**
 * Canonical behaviour for CraftFurnace inventory and burn/cook state bridge policy.
 */
public final class FurnaceBlockStateBehaviour {
    private static final FurnaceBlockStateBehaviour INSTANCE = new FurnaceBlockStateBehaviour();

    private FurnaceBlockStateBehaviour() {
    }

    public static FurnaceBlockStateBehaviour getInstance() {
        return INSTANCE;
    }

    public Inventory createInventory(TileEntityFurnace furnace) {
        return new CraftInventory(furnace);
    }

    public boolean finalizeUpdate(boolean parentUpdated, TileEntityFurnace furnace) {
        if (parentUpdated) {
            furnace.update();
        }
        return parentUpdated;
    }

    public short getBurnTime(TileEntityFurnace furnace) {
        return (short) furnace.burnTime;
    }

    public void setBurnTime(TileEntityFurnace furnace, short burnTime) {
        furnace.burnTime = burnTime;
    }

    public short getCookTime(TileEntityFurnace furnace) {
        return (short) furnace.cookTime;
    }

    public void setCookTime(TileEntityFurnace furnace, short cookTime) {
        furnace.cookTime = cookTime;
    }
}
