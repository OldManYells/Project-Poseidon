package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat furnace-smelt event scaffold.
 */
public class FurnaceSmeltEvent extends Event {
    private final Block furnace;
    private final com.legacyminecraft.compat.bukkit.inventory.ItemStack source;
    private com.legacyminecraft.compat.bukkit.inventory.ItemStack result;

    public FurnaceSmeltEvent(
            Block furnace,
            com.legacyminecraft.compat.bukkit.inventory.ItemStack source,
            com.legacyminecraft.compat.bukkit.inventory.ItemStack result
    ) {
        this.furnace = furnace;
        this.source = source;
        this.result = result;
    }

    public Block getFurnace() {
        return furnace;
    }

    public com.legacyminecraft.compat.bukkit.inventory.ItemStack getSource() {
        return source;
    }

    public com.legacyminecraft.compat.bukkit.inventory.ItemStack getResult() {
        return result;
    }

    public void setResult(com.legacyminecraft.compat.bukkit.inventory.ItemStack result) {
        this.result = result;
    }
}
