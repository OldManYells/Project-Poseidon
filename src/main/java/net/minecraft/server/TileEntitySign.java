package net.minecraft.server;

import com.legacyminecraft.poseidon.world.tile.SignTileBehaviour;

public class TileEntitySign extends TileEntity {
    private static final SignTileBehaviour SIGN_TILE_BEHAVIOUR = SignTileBehaviour.getInstance();

    public String[] lines = new String[] { "", "", "", ""};
    public int b = -1;
    private boolean isEditable = true;

    public TileEntitySign() {}

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        SIGN_TILE_BEHAVIOUR.writeLines(nbttagcompound, this.lines);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.isEditable = SIGN_TILE_BEHAVIOUR.markReadOnlyOnLoad();
        super.a(nbttagcompound);
        SIGN_TILE_BEHAVIOUR.readLines(nbttagcompound, this.lines);
    }

    public Packet f() {
        return SIGN_TILE_BEHAVIOUR.createUpdatePacket(this);
    }

    public boolean a() {
        return this.isEditable;
    }

    public void a(boolean flag) {
        this.isEditable = flag;
    }
}
