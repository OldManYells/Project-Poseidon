package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat sign tile-entity scaffold.
 */
public class TileEntitySign extends TileEntity {
    public final String[] lines = new String[]{"", "", "", ""};
    private boolean editable = true;

    public boolean a() {
        return editable;
    }

    public void a(boolean editable) {
        this.editable = editable;
    }
}

