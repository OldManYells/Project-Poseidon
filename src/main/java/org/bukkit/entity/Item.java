package org.bukkit.entity;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.item.*;

import org.bukkit.inventory.ItemStack;

/**
 * Represents an Item.
 *
 * @author Cogito
 *
 */
public interface Item extends Entity {

    /**
     * Gets the item stack associated with this item drop.
     *
     * @return
     */
    public ItemStack getItemStack();

    /**
     * Sets the item stack associated with this item drop.
     *
     * @param stack
     */
    public void setItemStack(ItemStack stack);
}
