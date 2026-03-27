package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local material scaffold.
 */
public enum Material {
    AIR(false),
    WATER(true),
    LAVA(true),
    SOLID(false);

    private final boolean liquid;

    Material(boolean liquid) {
        this.liquid = liquid;
    }

    public boolean isLiquid() {
        return liquid;
    }
}
