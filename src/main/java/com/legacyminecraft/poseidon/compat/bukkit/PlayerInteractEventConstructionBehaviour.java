package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Canonical behaviour for CraftEventFactory player-interact event construction.
 */
public final class PlayerInteractEventConstructionBehaviour {
    private static final PlayerInteractEventConstructionBehaviour INSTANCE =
            new PlayerInteractEventConstructionBehaviour();

    private PlayerInteractEventConstructionBehaviour() {
    }

    public static PlayerInteractEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public PlayerInteractEvent createPlayerInteractEvent(Player player, Action action, CraftItemStack itemInHand,
                                                         Block clickedBlock, BlockFace clickedFace) {
        return new PlayerInteractEvent(player, action, itemInHand, clickedBlock, clickedFace);
    }
}
