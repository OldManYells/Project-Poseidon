package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical map-cursor collection scaffold.
 */
public class MapCursorCollection {
    private final List<MapCursor> cursors = new ArrayList<MapCursor>();

    public int size() {
        return cursors.size();
    }

    public MapCursor getCursor(int index) {
        return cursors.get(index);
    }

    public void addCursor(byte x, byte y, byte direction, byte rawType) {
        cursors.add(new MapCursor(x, y, direction, rawType, true));
    }

    public void addCursor(MapCursor cursor) {
        cursors.add(cursor);
    }

    public void removeCursor(MapCursor cursor) {
        cursors.remove(cursor);
    }
}
