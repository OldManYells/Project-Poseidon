package com.legacyminecraft.poseidon.block;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

import org.bukkit.inventory.Inventory;

/**
 * Indicates a block type that has inventory.
 *
 * @author sk89q
 */
public interface IContainerBlock {

    /**
     * Get the block's inventory.
     *
     * @return
     */
    public Inventory getInventory();
}
