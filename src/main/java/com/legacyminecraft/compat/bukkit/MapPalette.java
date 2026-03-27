package com.legacyminecraft.compat.bukkit;

import java.awt.Image;

/**
 * Canonical map palette scaffold.
 */
public final class MapPalette {
    private MapPalette() {
    }

    public static final byte DARK_GRAY = 0;

    public static byte[] imageToBytes(Image image) {
        int width = image == null ? 0 : Math.max(image.getWidth(null), 0);
        int height = image == null ? 0 : Math.max(image.getHeight(null), 0);
        return new byte[width * height];
    }
}
