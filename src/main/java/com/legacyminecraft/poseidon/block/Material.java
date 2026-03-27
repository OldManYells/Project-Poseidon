package com.legacyminecraft.poseidon.block;

/**
 * Canonical block material scaffold.
 */
public class Material {
    public static final Material AIR = new Material(MaterialMapColor.b, false, 0);
    public static final Material WATER = new Material(MaterialMapColor.n, true, 1);
    public static final Material STATIONARY_WATER = new Material(MaterialMapColor.n, true, 1);
    public static final Material LAVA = new Material(MaterialMapColor.n, true, 2);
    public static final Material STATIONARY_LAVA = new Material(MaterialMapColor.n, true, 2);
    public static final Material PUMPKIN = new Material(MaterialMapColor.b, false, 0);

    public final MaterialMapColor C;
    private final boolean liquid;
    private final int pistonReactionId;

    public Material(MaterialMapColor mapColor, boolean liquid, int pistonReactionId) {
        this.C = mapColor;
        this.liquid = liquid;
        this.pistonReactionId = pistonReactionId;
    }

    public boolean isLiquid() {
        return liquid;
    }

    public boolean isBuildable() {
        return !liquid;
    }

    public boolean isSolid() {
        return !liquid;
    }

    public int j() {
        return pistonReactionId;
    }

    public boolean h() {
        return isSolid();
    }

    public boolean b() {
        return isBuildable();
    }
}
