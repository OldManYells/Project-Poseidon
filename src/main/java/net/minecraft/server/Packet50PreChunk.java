package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet50PreChunk extends Packet {

    public int a;
    public int b;
    public boolean c;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet50PreChunk() {
        this.k = false;
    }

    public Packet50PreChunk(int i, int j, boolean flag) {
        this.k = false;
        this.a = i;
        this.b = j;
        this.c = flag;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet50Data packetData = packetDataCodec.readPacket50(datainputstream);
        this.a = packetData.getChunkX();
        this.b = packetData.getChunkZ();
        this.c = packetData.isMode();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket50(new PacketDataCodec.Packet50Data(this.a, this.b, this.c), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet50Length();
    }
}
