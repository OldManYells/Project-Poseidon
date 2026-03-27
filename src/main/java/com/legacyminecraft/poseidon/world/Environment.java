package com.legacyminecraft.poseidon.world;

/**
 * World-local environment lookup helper.
 */
public final class Environment {
    private Environment() {
    }

    public static com.legacyminecraft.compat.bukkit.Environment getEnvironment(int dimension) {
        if (dimension == -1) {
            return com.legacyminecraft.compat.bukkit.Environment.NETHER;
        }
        if (dimension == 1) {
            return com.legacyminecraft.compat.bukkit.Environment.SKY;
        }
        return com.legacyminecraft.compat.bukkit.Environment.NORMAL;
    }
}
