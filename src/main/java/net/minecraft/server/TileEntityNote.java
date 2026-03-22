package net.minecraft.server;

import com.legacyminecraft.poseidon.world.tile.NoteBlockTileBehaviour;

public class TileEntityNote extends TileEntity {
    private static final NoteBlockTileBehaviour NOTE_BLOCK_TILE_BEHAVIOUR = NoteBlockTileBehaviour.getInstance();

    public byte note = 0;
    public boolean b = false;

    public TileEntityNote() {}

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        NOTE_BLOCK_TILE_BEHAVIOUR.writeNote(nbttagcompound, this.note);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.note = NOTE_BLOCK_TILE_BEHAVIOUR.readNote(nbttagcompound);
    }

    public void a() {
        this.note = NOTE_BLOCK_TILE_BEHAVIOUR.incrementNote(this.note);
        this.update();
    }

    public void play(World world, int i, int j, int k) {
        if (NOTE_BLOCK_TILE_BEHAVIOUR.canPlay(world, i, j, k)) {
            byte instrument = NOTE_BLOCK_TILE_BEHAVIOUR.resolveInstrument(world, i, j, k);
            world.playNote(i, j, k, instrument, this.note);
        }
    }
}
