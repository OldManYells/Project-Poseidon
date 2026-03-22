package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Packet60Explosion extends Packet {

    public double a;
    public double b;
    public double c;
    public float d;
    public Set e;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet60Explosion() {}

    public Packet60Explosion(double d0, double d1, double d2, float f, Set set) {
        this.a = d0;
        this.b = d1;
        this.c = d2;
        this.d = f;
        this.e = new HashSet(set);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet60Data packetData = packetDataCodec.readPacket60(datainputstream);
        this.a = packetData.getX();
        this.b = packetData.getY();
        this.c = packetData.getZ();
        this.d = packetData.getRadius();
        this.e = packetData.getExplodedBlocks();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket60(
                new PacketDataCodec.Packet60Data(this.a, this.b, this.c, this.d, this.e),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet60Length(this.e.size());
    }
}
