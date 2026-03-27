package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftSign line access and mutation.
 */
public final class SignLineAccessBehaviour {
    private static final SignLineAccessBehaviour INSTANCE = new SignLineAccessBehaviour();

    private SignLineAccessBehaviour() {
    }

    public static SignLineAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public String[] getLines(TileEntitySign sign) {
        return sign.lines;
    }

    public String getLine(TileEntitySign sign, int index) {
        return sign.lines[index];
    }

    public void setLine(TileEntitySign sign, int index, String line) {
        sign.lines[index] = line;
    }
}
