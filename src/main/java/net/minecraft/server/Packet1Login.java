package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet1Login extends Packet {

    public int a;
    public String name;
    public long c;
    public byte d;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet1Login() {}

    public Packet1Login(String s, int i, long j, byte b0) {
        this.name = s;
        this.a = i;
        this.c = j;
        this.d = b0;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet1Data packetData = packetDataCodec.readPacket1(datainputstream);
        this.a = packetData.getProtocolVersion();
        this.name = packetData.getUsername();
        this.c = packetData.getMapSeed();
        this.d = packetData.getDimension();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket1(
                new PacketDataCodec.Packet1Data(this.a, this.name, this.c, this.d),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet1Length(this.name);
    }
}
