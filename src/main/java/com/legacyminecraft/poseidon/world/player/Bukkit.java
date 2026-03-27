package com.legacyminecraft.poseidon.world.player;

/**
 * World-player local Bukkit alias.
 */
public final class Bukkit {
    private Bukkit() {
    }

    public static com.legacyminecraft.compat.bukkit.Server getServer() {
        return com.legacyminecraft.compat.bukkit.Bukkit.getServer();
    }
}

