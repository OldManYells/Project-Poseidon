package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet4UpdateTime extends Packet {

    public long a;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet4UpdateTime() {}

    public Packet4UpdateTime(long i) {
        this.a = i;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = packetDataCodec.readPacket4(datainputstream).getWorldTime();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket4(new PacketDataCodec.Packet4Data(this.a), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet4Length();
    }
}
