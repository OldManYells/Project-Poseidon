package com.legacyminecraft.poseidon.network;

import java.util.UUID;

/**
 * Network-local player facade.
 */
public class Player implements com.legacyminecraft.compat.bukkit.Player {
    private final UUID uniqueId = new UUID(0L, 0L);
    private String name = "player";
    private Object world;
    private Location location = new Location(null, 0.0D, 0.0D, 0.0D);
    private boolean online = true;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public Object getWorld() {
        return world;
    }

    public Location getLocation() {
        return location;
    }

    public Location getEyeLocation() {
        return location;
    }

    public void teleport(Location destination) {
        this.world = destination.getWorld();
        this.location = destination.clone();
    }

    public void sendMessage(String message) {
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
