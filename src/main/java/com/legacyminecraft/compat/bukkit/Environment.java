package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat world-environment enum.
 */
public enum Environment {
    NORMAL(0),
    NETHER(-1),
    SKY(1);

    private final int id;

    Environment(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

