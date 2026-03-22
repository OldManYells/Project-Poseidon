package net.minecraft.server;

import com.legacyminecraft.poseidon.world.tile.JukeboxTileBehaviour;

public class TileEntityRecordPlayer extends TileEntity {
    private static final JukeboxTileBehaviour JUKEBOX_TILE_BEHAVIOUR = JukeboxTileBehaviour.getInstance();

    public int a;

    public TileEntityRecordPlayer() {}

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = JUKEBOX_TILE_BEHAVIOUR.readRecordId(nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        JUKEBOX_TILE_BEHAVIOUR.writeRecordIdIfPresent(nbttagcompound, this.a);
    }
}
