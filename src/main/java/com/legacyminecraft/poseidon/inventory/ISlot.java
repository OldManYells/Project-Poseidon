package com.legacyminecraft.poseidon.inventory;
import com.legacyminecraft.poseidon.world.item.*;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a slot in an inventory
 */
public interface ISlot {

    /**
     * Gets the inventory this slot belongs to
     *
     * @return The inventory
     */
    public Inventory getInventory();

    /**
     * Get the index this slot belongs to
     *
     * @return Index of the slot
     */
    public int getIndex();

    /**
     * Get the item from the slot.
     *
     * @return ItemStack in the slot.
     */
    public ItemStack getItem();
}
