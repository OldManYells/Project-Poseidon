package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat dye-color scaffold.
 */
public enum DyeColor {
    WHITE(0),
    ORANGE(1),
    MAGENTA(2),
    LIGHT_BLUE(3),
    YELLOW(4),
    LIME(5),
    PINK(6),
    GRAY(7),
    LIGHT_GRAY(8),
    CYAN(9),
    PURPLE(10),
    BLUE(11),
    BROWN(12),
    GREEN(13),
    RED(14),
    BLACK(15);

    private final byte data;

    DyeColor(int data) {
        this.data = (byte) data;
    }

    public byte getData() {
        return data;
    }

    public static DyeColor getByData(byte data) {
        int index = data & 15;
        for (DyeColor dyeColor : values()) {
            if (dyeColor.data == (byte) index) {
                return dyeColor;
            }
        }
        return WHITE;
    }
}
