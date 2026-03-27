package com.legacyminecraft.poseidon.block;

/**
 * Canonical material map-color scaffold.
 */
public class MaterialMapColor {
    public static final MaterialMapColor b = new MaterialMapColor(0);
    public static final MaterialMapColor n = new MaterialMapColor(1);

    public final int q;

    public MaterialMapColor(int colorIndex) {
        this.q = colorIndex;
    }
}
