package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet22Collect extends Packet {

    public int a;
    public int b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet22Collect() {}

    public Packet22Collect(int i, int j) {
        this.a = i;
        this.b = j;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet22Data packetData = packetDataCodec.readPacket22(datainputstream);
        this.a = packetData.getCollectedEntityId();
        this.b = packetData.getCollectorEntityId();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket22(new PacketDataCodec.Packet22Data(this.a, this.b), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet22Length();
    }
}
