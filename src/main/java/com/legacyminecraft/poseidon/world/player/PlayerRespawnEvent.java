package com.legacyminecraft.poseidon.world.player;

/**
 * World-player local PlayerRespawnEvent alias.
 */
public class PlayerRespawnEvent extends com.legacyminecraft.compat.bukkit.PlayerRespawnEvent {
    public PlayerRespawnEvent(Player player, Location respawnLocation, boolean bedSpawn) {
        super(player, respawnLocation, bedSpawn);
    }
}

