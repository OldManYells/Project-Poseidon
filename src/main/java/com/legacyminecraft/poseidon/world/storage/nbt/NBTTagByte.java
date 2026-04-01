package com.legacyminecraft.poseidon.world.storage.nbt;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase {

    public byte a;

    public NBTTagByte() {}

    public NBTTagByte(byte b0) {
        this.a = b0;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeByte(this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = datainput.readByte();
    }

    public byte a() {
        return (byte) 1;
    }

    public String toString() {
        return "" + this.a;
    }
}
