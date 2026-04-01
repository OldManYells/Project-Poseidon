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

public class NBTTagShort extends NBTBase {

    public short a;

    public NBTTagShort() {}

    public NBTTagShort(short short1) {
        this.a = short1;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeShort(this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = datainput.readShort();
    }

    public byte a() {
        return (byte) 2;
    }

    public String toString() {
        return "" + this.a;
    }
}
