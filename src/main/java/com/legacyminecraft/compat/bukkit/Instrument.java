package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat note instrument scaffold.
 */
public enum Instrument {
    PIANO((byte) 0),
    BASS_DRUM((byte) 1),
    SNARE_DRUM((byte) 2),
    STICKS((byte) 3),
    BASS_GUITAR((byte) 4);

    private final byte type;

    Instrument(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}
