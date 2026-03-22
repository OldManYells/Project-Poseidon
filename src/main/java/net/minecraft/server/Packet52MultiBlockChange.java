package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet52MultiBlockChange extends Packet {

    public int a;
    public int b;
    public short[] c;
    public byte[] d;
    public byte[] e;
    public int f;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet52MultiBlockChange() {
        this.k = true;
    }

    public Packet52MultiBlockChange(int i, int j, short[] ashort, int k, World world) {
        this.k = true;
        this.a = i;
        this.b = j;
        this.f = k;
        this.c = new short[k];
        this.d = new byte[k];
        this.e = new byte[k];
        Chunk chunk = world.getChunkAt(i, j);

        for (int l = 0; l < k; ++l) {
            int i1 = ashort[l] >> 12 & 15;
            int j1 = ashort[l] >> 8 & 15;
            int k1 = ashort[l] & 255;

            this.c[l] = ashort[l];
            this.d[l] = (byte) chunk.getTypeId(i1, k1, j1);
            this.e[l] = (byte) chunk.getData(i1, k1, j1);
        }
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet52Data packetData = packetDataCodec.readPacket52(datainputstream);
        this.a = packetData.getChunkX();
        this.b = packetData.getChunkZ();
        this.f = packetData.getRecordCount();
        this.c = packetData.getCoordinates();
        this.d = packetData.getTypeIds();
        this.e = packetData.getMetadata();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket52(
                new PacketDataCodec.Packet52Data(this.a, this.b, this.c, this.d, this.e, this.f),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet52Length(this.f);
    }
}
