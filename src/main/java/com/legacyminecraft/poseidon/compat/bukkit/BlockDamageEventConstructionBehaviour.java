package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;

/**
 * Canonical behaviour for CraftEventFactory block-damage event construction.
 */
public final class BlockDamageEventConstructionBehaviour {
    private static final BlockDamageEventConstructionBehaviour INSTANCE = new BlockDamageEventConstructionBehaviour();

    private BlockDamageEventConstructionBehaviour() {
    }

    public static BlockDamageEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public BlockDamageEvent createBlockDamageEvent(Player player, Block clickedBlock, CraftItemStack itemInHand,
                                                   boolean instaBreak) {
        return new BlockDamageEvent(player, clickedBlock, itemInHand, instaBreak);
    }
}
