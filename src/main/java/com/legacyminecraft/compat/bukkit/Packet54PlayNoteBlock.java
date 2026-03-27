package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat note-block packet scaffold.
 */
public class Packet54PlayNoteBlock extends Packet {
    public final int x;
    public final int y;
    public final int z;
    public final byte instrument;
    public final byte note;

    public Packet54PlayNoteBlock(int x, int y, int z, byte instrument, byte note) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.instrument = instrument;
        this.note = note;
    }
}
