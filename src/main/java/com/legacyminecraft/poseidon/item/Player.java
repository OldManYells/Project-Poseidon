package com.legacyminecraft.poseidon.item;

/**
 * Item-behaviour player facade.
 */
public class Player {
    private final Bukkit.BukkitServer server = Bukkit.getServer();

    public Bukkit.BukkitServer getServer() {
        return server;
    }

    public void updateInventory() {
    }
}
