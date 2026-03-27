package com.legacyminecraft.compat.bukkit;

/**
 * Canonical map-canvas scaffold.
 */
public interface MapCanvas {
    void setPixel(int x, int y, byte color);

    MapCursorCollection getCursors();
}
