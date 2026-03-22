package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet14BlockDig extends Packet {

    public int a;
    public int b;
    public int c;
    public int face;
    public int e;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet14BlockDig() {}

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet14Data packetData = packetDataCodec.readPacket14(datainputstream);
        this.e = packetData.getStatus();
        this.a = packetData.getX();
        this.b = packetData.getY();
        this.c = packetData.getZ();
        this.face = packetData.getFace();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket14(
                new PacketDataCodec.Packet14Data(this.e, this.a, this.b, this.c, this.face),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet14Length();
    }
}
