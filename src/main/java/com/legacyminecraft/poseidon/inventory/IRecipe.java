package com.legacyminecraft.poseidon.inventory;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
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
