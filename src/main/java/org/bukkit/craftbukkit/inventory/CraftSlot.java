package org.bukkit.craftbukkit.inventory;

import com.legacy.minecraft.poseidon.Slot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftSlot implements org.bukkit.inventory.Slot {
    private final Slot slot;

    public CraftSlot(Slot slot) {
        this.slot = slot;
    }

    public Inventory getInventory() {
        return new CraftInventory(slot.inventory);
    }

    public int getIndex() {
        return slot.index;
    }

    public ItemStack getItem() {
        return new CraftItemStack(slot.getItem());
    }
}
