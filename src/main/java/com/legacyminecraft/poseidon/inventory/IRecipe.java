package com.legacyminecraft.poseidon.inventory;
import com.legacyminecraft.poseidon.world.item.*;

import org.bukkit.inventory.ItemStack;

/**
 * Represents some type of crafting recipe.
 */
public interface IRecipe {

    /**
     * Get the result of this recipe.
     * @return The result stack
     */
    ItemStack getResult();
}
