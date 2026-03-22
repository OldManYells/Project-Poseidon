package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet61 extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet61() {}

    public Packet61(int i, int j, int k, int l, int i1) {
        this.a = i;
        this.c = j;
        this.d = k;
        this.e = l;
        this.b = i1;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet61Data data = packetDataCodec.readPacket61(datainputstream);
        this.a = data.getPrimaryId();
        this.c = data.getX();
        this.d = data.getY();
        this.e = data.getZ();
        this.b = data.getValue();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket61(
                new PacketDataCodec.Packet61Data(this.a, this.c, this.d, this.e, this.b),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet61Length();
    }
}
