package com.legacyminecraft.compat.bukkit;

/**
 * Canonical map scale scaffold.
 */
public enum Scale {
    CLOSEST((byte) 0),
    CLOSE((byte) 1),
    NORMAL((byte) 2),
    FAR((byte) 3),
    FARTHEST((byte) 4);

    private final byte value;

    Scale(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static Scale valueOf(byte value) {
        for (Scale scale : values()) {
            if (scale.value == value) {
                return scale;
            }
        }
        return NORMAL;
    }
}
