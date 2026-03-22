package org.bukkit.craftbukkit.block;

import com.legacyminecraft.poseidon.compat.bukkit.NoteBlockPlaybackBehaviour;
import net.minecraft.server.TileEntityNote;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.NoteBlock;
import org.bukkit.craftbukkit.CraftWorld;

public class CraftNoteBlock extends CraftBlockState implements NoteBlock {
    private static final NoteBlockPlaybackBehaviour NOTE_BLOCK_PLAYBACK_BEHAVIOUR =
            NoteBlockPlaybackBehaviour.getInstance();
    private final CraftWorld world;
    private final TileEntityNote note;

    public CraftNoteBlock(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        note = (TileEntityNote) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Note getNote() {
        return NOTE_BLOCK_PLAYBACK_BEHAVIOUR.getNote(note);
    }

    public byte getRawNote() {
        return NOTE_BLOCK_PLAYBACK_BEHAVIOUR.getRawNote(note);
    }

    public void setNote(Note n) {
        NOTE_BLOCK_PLAYBACK_BEHAVIOUR.setNote(note, n);
    }

    public void setRawNote(byte n) {
        NOTE_BLOCK_PLAYBACK_BEHAVIOUR.setRawNote(note, n);
    }

    public boolean play() {
        return NOTE_BLOCK_PLAYBACK_BEHAVIOUR.playStoredNote(getBlock(), note, world.getHandle(), getX(), getY(), getZ());
    }

    public boolean play(byte instrument, byte note) {
        return NOTE_BLOCK_PLAYBACK_BEHAVIOUR.playRawNote(getBlock(), world.getHandle(), getX(), getY(), getZ(), instrument, note);
    }

    public boolean play(Instrument instrument, Note note) {
        return NOTE_BLOCK_PLAYBACK_BEHAVIOUR.playNote(getBlock(), world.getHandle(), getX(), getY(), getZ(), instrument, note);
    }
}
