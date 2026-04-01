package com.legacyminecraft.poseidon.world.block.entity;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.world.storage.nbt.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.world.entity.*;

public class TileEntityNote extends TileEntity {

    public byte note = 0;
    public boolean b = false;

    public TileEntityNote() {}

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("note", this.note);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.note = nbttagcompound.c("note");
        if (this.note < 0) {
            this.note = 0;
        }

        if (this.note > 24) {
            this.note = 24;
        }
    }

    public void a() {
        this.note = (byte) ((this.note + 1) % 25);
        this.update();
    }

    public void play(World world, int i, int j, int k) {
        if (world.getMaterial(i, j + 1, k) == Material.AIR) {
            Material material = world.getMaterial(i, j - 1, k);
            byte b0 = 0;

            if (material == Material.STONE) {
                b0 = 1;
            }

            if (material == Material.SAND) {
                b0 = 2;
            }

            if (material == Material.SHATTERABLE) {
                b0 = 3;
            }

            if (material == Material.WOOD) {
                b0 = 4;
            }

            world.playNote(i, j, k, b0, this.note);
        }
    }
}
