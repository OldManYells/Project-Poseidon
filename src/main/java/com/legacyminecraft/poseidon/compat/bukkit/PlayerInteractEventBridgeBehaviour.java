package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Canonical compat bridge for invoking CraftBukkit player interact event hooks.
 */
public final class PlayerInteractEventBridgeBehaviour {
    private static final PlayerInteractEventBridgeBehaviour INSTANCE = new PlayerInteractEventBridgeBehaviour();

    private PlayerInteractEventBridgeBehaviour() {
    }

    public static PlayerInteractEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public PlayerInteractEvent callPlayerInteract(EntityHuman player, Action action, ItemStack itemInHand) {
        return CraftEventFactory.callPlayerInteractEvent(player, action, itemInHand);
    }

    public PlayerInteractEvent callPlayerInteract(
            EntityHuman player,
            Action action,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            ItemStack itemInHand
    ) {
        return CraftEventFactory.callPlayerInteractEvent(
                player,
                action,
                clickedX,
                clickedY,
                clickedZ,
                clickedFace,
                itemInHand
        );
    }
}
