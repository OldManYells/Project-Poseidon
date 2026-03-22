package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.TileEntityNote;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;

/**
 * Canonical behaviour for CraftNoteBlock note state and playback policy.
 */
public final class NoteBlockPlaybackBehaviour {
    private static final NoteBlockPlaybackBehaviour INSTANCE = new NoteBlockPlaybackBehaviour();

    private NoteBlockPlaybackBehaviour() {
    }

    public static NoteBlockPlaybackBehaviour getInstance() {
        return INSTANCE;
    }

    public Note getNote(TileEntityNote noteTile) {
        return new Note(noteTile.note);
    }

    public byte getRawNote(TileEntityNote noteTile) {
        return noteTile.note;
    }

    public void setNote(TileEntityNote noteTile, Note note) {
        noteTile.note = note.getId();
    }

    public void setRawNote(TileEntityNote noteTile, byte rawNote) {
        noteTile.note = rawNote;
    }

    public boolean playStoredNote(Block block, TileEntityNote noteTile, net.minecraft.server.World world, int x, int y, int z) {
        synchronized (block) {
            if (block.getType() != Material.NOTE_BLOCK) {
                return false;
            }
            noteTile.play(world, x, y, z);
            return true;
        }
    }

    public boolean playRawNote(Block block, net.minecraft.server.World world, int x, int y, int z, byte instrument, byte note) {
        synchronized (block) {
            if (block.getType() != Material.NOTE_BLOCK) {
                return false;
            }
            world.playNote(x, y, z, instrument, note);
            return true;
        }
    }

    public boolean playNote(Block block, net.minecraft.server.World world, int x, int y, int z, Instrument instrument, Note note) {
        synchronized (block) {
            if (block.getType() != Material.NOTE_BLOCK) {
                return false;
            }
            world.playNote(x, y, z, instrument.getType(), note.getId());
            return true;
        }
    }
}
