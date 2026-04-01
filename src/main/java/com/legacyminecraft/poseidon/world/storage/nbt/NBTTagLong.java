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

public class NBTTagLong extends NBTBase {

    public long a;

    public NBTTagLong() {}

    public NBTTagLong(long i) {
        this.a = i;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeLong(this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = datainput.readLong();
    }

    public byte a() {
        return (byte) 4;
    }

    public String toString() {
        return "" + this.a;
    }
}
