package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.Action;

/**
 * Canonical behaviour for CraftEventFactory interaction event input normalization.
 */
public final class PlayerInteractPreparationBehaviour {
    private static final PlayerInteractPreparationBehaviour INSTANCE = new PlayerInteractPreparationBehaviour();

    private PlayerInteractPreparationBehaviour() {
    }

    public static PlayerInteractPreparationBehaviour getInstance() {
        return INSTANCE;
    }

    public PreparedInteraction prepare(Action requestedAction, int clickedY, CraftItemStack itemInHand) {
        Action resolvedAction = requestedAction;
        boolean useNullClickedBlock = clickedY == 255;

        if (clickedY == 255) {
            if (requestedAction == Action.LEFT_CLICK_BLOCK) {
                resolvedAction = Action.LEFT_CLICK_AIR;
            } else if (requestedAction == Action.RIGHT_CLICK_BLOCK) {
                resolvedAction = Action.RIGHT_CLICK_AIR;
            }
        }

        CraftItemStack normalizedItemInHand = itemInHand;
        if (itemInHand != null && (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0)) {
            normalizedItemInHand = null;
        }

        return new PreparedInteraction(resolvedAction, normalizedItemInHand, useNullClickedBlock);
    }

    public static final class PreparedInteraction {
        private final Action action;
        private final CraftItemStack itemInHand;
        private final boolean nullClickedBlock;

        public PreparedInteraction(Action action, CraftItemStack itemInHand, boolean nullClickedBlock) {
            this.action = action;
            this.itemInHand = itemInHand;
            this.nullClickedBlock = nullClickedBlock;
        }

        public Action getAction() {
            return action;
        }

        public CraftItemStack getItemInHand() {
            return itemInHand;
        }

        public boolean useNullClickedBlock() {
            return nullClickedBlock;
        }
    }
}
