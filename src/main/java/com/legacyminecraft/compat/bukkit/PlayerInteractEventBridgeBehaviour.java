package com.legacyminecraft.compat.bukkit;


/**
 * Canonical compat bridge for invoking CraftBukkit player interact event hooks.
 */
public final class PlayerInteractEventBridgeBehaviour {
    private static final PlayerInteractEventBridgeBehaviour INSTANCE = new PlayerInteractEventBridgeBehaviour();
    private static final EventFactoryInteractionSystem EVENT_FACTORY_INTERACTION_SYSTEM =
            EventFactoryInteractionSystem.getInstance();

    private PlayerInteractEventBridgeBehaviour() {
    }

    public static PlayerInteractEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public PlayerInteractEvent callPlayerInteract(EntityHuman player, Action action, ItemStack itemInHand) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerInteractEvent(player, action, itemInHand);
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
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerInteractEvent(
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
