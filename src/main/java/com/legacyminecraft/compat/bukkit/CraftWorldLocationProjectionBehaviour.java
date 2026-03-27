package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld location-overload projection and forwarding.
 */
public final class CraftWorldLocationProjectionBehaviour {
    private static final CraftWorldLocationProjectionBehaviour INSTANCE = new CraftWorldLocationProjectionBehaviour();

    private CraftWorldLocationProjectionBehaviour() {
    }

    public static CraftWorldLocationProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    public Block getBlockAt(CraftWorld craftWorld, Location location) {
        return craftWorld.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getBlockTypeIdAt(CraftWorld craftWorld, Location location) {
        return craftWorld.getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getHighestBlockYAt(CraftWorld craftWorld, Location location) {
        return craftWorld.getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    public Chunk getChunkAt(CraftWorld craftWorld, Location location) {
        return craftWorld.getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    public Block getHighestBlockAt(CraftWorld craftWorld, Location location) {
        return craftWorld.getHighestBlockAt(location.getBlockX(), location.getBlockZ());
    }
}
