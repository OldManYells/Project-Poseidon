package com.legacyminecraft.poseidon.world.player;

/**
 * World-player local PlayerPortalEvent alias.
 */
public class PlayerPortalEvent extends com.legacyminecraft.compat.bukkit.PlayerPortalEvent {
    public PlayerPortalEvent(Player player, Location from, Location to, TravelAgent travelAgent) {
        super(player, from, to, travelAgent);
    }
}

