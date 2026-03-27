package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat note tile-entity scaffold.
 */
public class TileEntityNote extends TileEntity {
    public byte note;

    public void play(World world, int x, int y, int z) {
        world.playNote(x, y, z, (byte) 0, note);
    }
}
