package com.legacyminecraft.poseidon.item;

/**
 * Item-local material scaffold.
 */
public enum Material {
    AIR(false),
    WATER(true),
    LAVA(true),
    STONE(false),
    ORE(false),
    SOLID(false);

    private final boolean liquid;

    Material(boolean liquid) {
        this.liquid = liquid;
    }

    public boolean isLiquid() {
        return liquid;
    }

    public boolean isBuildable() {
        return this != AIR && !liquid;
    }
}
