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

public class NBTTagString extends NBTBase {

    public String a;

    public NBTTagString() {}

    public NBTTagString(String s) {
        this.a = s;
        if (s == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeUTF(this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = datainput.readUTF();
    }

    public byte a() {
        return (byte) 8;
    }

    public String toString() {
        return "" + this.a;
    }
}
