package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet40EntityMetadata extends Packet {

    public int a;
    private List b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet40EntityMetadata() {}

    public Packet40EntityMetadata(int i, DataWatcher datawatcher) {
        this.a = i;
        this.b = datawatcher.b();
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet40Data packetData = packetDataCodec.readPacket40(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getMetadata();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket40(
                new PacketDataCodec.Packet40Data(this.a, this.b),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet40Length();
    }
}
