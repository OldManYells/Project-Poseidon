package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.nbt.NBTTagCompound;

/**
 * Canonical tile-entity base scaffold.
 */
public class TileEntity {
    public World world;
    public int x;
    public int y;
    public int z;
    protected boolean h;

    public void a(NBTTagCompound tag) {
    }

    public boolean g() {
        return this.h;
    }

    public void h() {
        this.h = true;
    }

    public void j() {
        this.h = false;
    }
}
