package com.legacyminecraft.poseidon.world;

/**
 * Minimal material model for world queries and generation logic.
 */
public final class Material {
    public static final Material AIR = new Material(false, false);
    public static final Material WATER = new Material(false, false);
    public static final Material STATIONARY_WATER = new Material(false, false);
    public static final Material LAVA = new Material(false, false);
    public static final Material STATIONARY_LAVA = new Material(false, false);
    public static final Material STONE = new Material(true, true);
    public static final Material SAND = new Material(true, true);
    public static final Material SHATTERABLE = new Material(true, true);
    public static final Material WOOD = new Material(true, true);
    public static final Material DIRT = new Material(true, true);

    private final boolean buildable;
    private final boolean solid;

    private Material(boolean buildable, boolean solid) {
        this.buildable = buildable;
        this.solid = solid;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public boolean isSolid() {
        return solid;
    }
}
