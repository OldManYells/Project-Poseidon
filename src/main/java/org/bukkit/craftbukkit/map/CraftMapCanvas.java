package org.bukkit.craftbukkit.map;

import com.legacyminecraft.compat.bukkit.CraftMapCanvasBehaviour;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;

import java.awt.*;

public class CraftMapCanvas implements MapCanvas {
    private static final CraftMapCanvasBehaviour CRAFT_MAP_CANVAS_BEHAVIOUR =
            CraftMapCanvasBehaviour.getInstance();

    private final byte[] buffer = new byte[128 * 128];
    private final CraftMapView mapView;
    private byte[] base;
    private MapCursorCollection cursors = new MapCursorCollection();

    public CraftMapCanvas(CraftMapView mapView) {
        this.mapView = mapView;
        CRAFT_MAP_CANVAS_BEHAVIOUR.initializeBuffer(buffer);
    }

    public CraftMapView getMapView() {
        return mapView;
    }

    public MapCursorCollection getCursors() {
        return cursors;
    }

    public void setCursors(MapCursorCollection cursors) {
        this.cursors = cursors;
    }

    public void setPixel(int x, int y, byte color) {
        CRAFT_MAP_CANVAS_BEHAVIOUR.setPixel(mapView.worldMap, buffer, x, y, color);
    }

    public byte getPixel(int x, int y) {
        return CRAFT_MAP_CANVAS_BEHAVIOUR.getPixel(buffer, x, y);
    }

    public byte getBasePixel(int x, int y) {
        return CRAFT_MAP_CANVAS_BEHAVIOUR.getBasePixel(base, x, y);
    }

    public void setBase(byte[] base) {
        this.base = base;
    }
    
    public byte[] getBuffer() {
        return buffer;
    }

    public void drawImage(int x, int y, Image image) {
        CRAFT_MAP_CANVAS_BEHAVIOUR.drawImage(mapView.worldMap, buffer, x, y, image);
    }

    public void drawText(int x, int y, MapFont font, String text) {
        CRAFT_MAP_CANVAS_BEHAVIOUR.drawText(mapView.worldMap, buffer, x, y, font, text);
    }
}
