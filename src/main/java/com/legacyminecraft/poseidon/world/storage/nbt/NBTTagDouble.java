package com.legacyminecraft.poseidon.world.storage.nbt;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {

    public double a;

    public NBTTagDouble() {}

    public NBTTagDouble(double d0) {
        this.a = d0;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeDouble(this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = datainput.readDouble();
    }

    public byte a() {
        return (byte) 6;
    }

    public String toString() {
        return "" + this.a;
    }
}
