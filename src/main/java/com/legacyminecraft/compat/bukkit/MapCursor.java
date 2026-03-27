package com.legacyminecraft.compat.bukkit;

/**
 * Canonical map cursor scaffold.
 */
public class MapCursor {
    private final int x;
    private final int y;
    private final byte direction;
    private final byte rawType;
    private final boolean visible;

    public MapCursor() {
        this(0, 0, (byte) 0, (byte) 0, true);
    }

    public MapCursor(int x, int y, byte direction, byte rawType, boolean visible) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.rawType = rawType;
        this.visible = visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public byte getDirection() {
        return direction;
    }

    public byte getRawType() {
        return rawType;
    }

    public boolean isVisible() {
        return visible;
    }
}
