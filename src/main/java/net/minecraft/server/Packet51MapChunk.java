package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet51MapChunk extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public byte[] g;
    public int h; // CraftBukkit - private -> public
    public byte[] rawData; // CraftBukkit
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet51MapChunk() {
        this.k = true;
    }

    // CraftBukkit start
    public Packet51MapChunk(int i, int j, int k, int l, int i1, int j1, World world) {
        this(i, j, k, l, i1, j1, world.getMultiChunkData(i, j, k, l, i1, j1));
    }

    public Packet51MapChunk(int i, int j, int k, int l, int i1, int j1, byte[] data) {
        // CraftBukkit end
        this.k = true;
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = l;
        this.e = i1;
        this.f = j1;
        /* CraftBukkit - Moved compression into its own method.
        byte[] abyte = data; // CraftBukkit - uses data from above constructor
        Deflater deflater = new Deflater(-1);

        try {
            deflater.setInput(abyte);
            deflater.finish();
            this.g = new byte[l * i1 * j1 * 5 / 2];
            this.h = deflater.deflate(this.g);
        } finally {
            deflater.end();
        }*/
        this.rawData = data; // CraftBukkit
    }

    public void a(DataInputStream datainputstream) throws IOException { // CraftBukkit - throws IOEXception
        PacketDataCodec.Packet51ReadData packetData = packetDataCodec.readPacket51(datainputstream);
        this.a = packetData.getX();
        this.b = packetData.getY();
        this.c = packetData.getZ();
        this.d = packetData.getSizeX();
        this.e = packetData.getSizeY();
        this.f = packetData.getSizeZ();
        this.h = packetData.getCompressedLength();
        this.g = packetData.getChunkData();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException { // CraftBukkit - throws IOException
        packetDataCodec.writePacket51(
                new PacketDataCodec.Packet51WriteData(this.a, this.b, this.c, this.d, this.e, this.f, this.h, this.g),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet51Length(this.h);
    }
}
