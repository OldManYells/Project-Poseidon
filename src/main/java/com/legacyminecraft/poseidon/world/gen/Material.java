package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation material scaffold.
 */
public class Material {
    public static final Material AIR = new Material(false);
    public static final Material SOLID = new Material(true);

    private final boolean buildable;

    public Material(boolean buildable) {
        this.buildable = buildable;
    }

    public boolean isBuildable() {
        return buildable;
    }
}
