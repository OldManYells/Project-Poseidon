package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat note-value scaffold.
 */
public class Note {
    private final byte id;

    public Note(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }
}
