package com.legacyminecraft.poseidon.world.map;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.storage.nbt.*;
import com.legacyminecraft.poseidon.*;

public abstract class WorldMapBase {

    public final String a;
    private boolean b;

    public WorldMapBase(String s) {
        this.a = s;
    }

    public abstract void a(NBTTagCompound nbttagcompound);

    public abstract void b(NBTTagCompound nbttagcompound);

    public void a() {
        this.a(true);
    }

    public void a(boolean flag) {
        this.b = flag;
    }

    public boolean b() {
        return this.b;
    }
}
