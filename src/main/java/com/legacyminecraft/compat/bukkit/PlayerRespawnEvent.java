package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat player-respawn event scaffold.
 */
public class PlayerRespawnEvent extends Event {
    private final Player player;
    private Location respawnLocation;
    private final boolean bedSpawn;

    public PlayerRespawnEvent(Player player, Location respawnLocation, boolean bedSpawn) {
        this.player = player;
        this.respawnLocation = respawnLocation;
        this.bedSpawn = bedSpawn;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation(Location respawnLocation) {
        this.respawnLocation = respawnLocation;
    }

    public boolean isBedSpawn() {
        return bedSpawn;
    }
}

