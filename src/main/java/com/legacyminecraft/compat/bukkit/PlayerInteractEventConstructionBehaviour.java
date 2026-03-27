package com.legacyminecraft.compat.bukkit;


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
