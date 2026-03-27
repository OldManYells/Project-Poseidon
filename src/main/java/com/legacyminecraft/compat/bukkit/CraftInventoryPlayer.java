package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat craft inventory scaffold.
 */
public class CraftInventoryPlayer implements PlayerInventory {
    private final InventoryPlayer inventory;

    public CraftInventoryPlayer(InventoryPlayer inventory) {
        this.inventory = inventory;
    }

    public InventoryPlayer getInventory() {
        return inventory;
    }

    public ItemStack getItem(int index) {
        return inventory.getItem(index);
    }

    public void setItem(int index, ItemStack itemStack) {
        inventory.setItem(index, itemStack);
    }

    public int getSize() {
        return inventory.getSize();
    }
}
