package net.minecraft.server;

import com.legacyminecraft.poseidon.world.RegionChunkBufferBehaviour;

import java.io.ByteArrayOutputStream;

class ChunkBuffer extends ByteArrayOutputStream {
    private static final RegionChunkBufferBehaviour REGION_CHUNK_BUFFER_BEHAVIOUR = RegionChunkBufferBehaviour.getInstance();

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
        REGION_CHUNK_BUFFER_BEHAVIOUR.flushToRegion(this.a, this.b, this.c, this.buf, this.count);
    }
}
