package com.legacyminecraft.poseidon.world.storage;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

import java.io.ByteArrayOutputStream;

class ChunkBuffer extends ByteArrayOutputStream {

    private int b;
    private int c;

    final RegionFile a;

    public ChunkBuffer(RegionFile regionfile, int i, int j) {
        super(8096);
        this.a = regionfile;
        this.b = i;
        this.c = j;
    }

    public void close() {
        this.a.a(this.b, this.c, this.buf, this.count);
    }
}
