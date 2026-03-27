package com.legacyminecraft.compat.bukkit;

/**
 * Canonical CraftMapCanvas scaffold.
 */
public class CraftMapCanvas implements MapCanvas {
    private final CraftMapView mapView;
    private final byte[] buffer = new byte[128 * 128];
    private final MapCursorCollection cursors = new MapCursorCollection();

    public CraftMapCanvas(CraftMapView mapView) {
        this.mapView = mapView;
        setBase(buffer);
    }

    public void setBase(byte[] base) {
        if (base == null || base.length != buffer.length) {
            return;
        }
        System.arraycopy(base, 0, buffer, 0, base.length);
    }

    public byte[] getBuffer() {
        return buffer;
    }

    @Override
    public void setPixel(int x, int y, byte color) {
        if (x < 0 || y < 0 || x >= 128 || y >= 128) {
            return;
        }
        buffer[y * 128 + x] = color;
    }

    @Override
    public MapCursorCollection getCursors() {
        return cursors;
    }

    public CraftMapView getMapView() {
        return mapView;
    }
}
