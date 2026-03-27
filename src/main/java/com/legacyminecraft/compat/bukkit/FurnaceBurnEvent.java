package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat furnace-burn event scaffold.
 */
public class FurnaceBurnEvent extends Event {
    private final Block furnace;
    private final com.legacyminecraft.compat.bukkit.inventory.ItemStack fuel;
    private int burnTime;
    private boolean burning;

    public FurnaceBurnEvent(Block furnace, com.legacyminecraft.compat.bukkit.inventory.ItemStack fuel, int burnTime) {
        this.furnace = furnace;
        this.fuel = fuel;
        this.burnTime = burnTime;
        this.burning = true;
    }

    public Block getFurnace() {
        return furnace;
    }

    public com.legacyminecraft.compat.bukkit.inventory.ItemStack getFuel() {
        return fuel;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }
}
