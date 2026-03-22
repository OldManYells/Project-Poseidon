package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet7UseEntity extends Packet {

    public int a;
    public int target;
    public int c;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet7UseEntity() {}

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet7Data packetData = packetDataCodec.readPacket7(datainputstream);
        this.a = packetData.getUserEntityId();
        this.target = packetData.getTargetEntityId();
        this.c = packetData.getInteractionType();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket7(
                new PacketDataCodec.Packet7Data(this.a, this.target, this.c),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet7Length();
    }
}
