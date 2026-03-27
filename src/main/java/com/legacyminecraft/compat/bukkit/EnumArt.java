package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat painting-art scaffold.
 */
public enum EnumArt {
    KEBAB("Kebab"),
    AZTEC("Aztec"),
    ALBAN("Alban"),
    AZTEC2("Aztec2");

    public static final int z = 256;

    public final String A;

    EnumArt(String name) {
        this.A = name;
    }
}
