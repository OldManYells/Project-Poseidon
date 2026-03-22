package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet18ArmAnimation extends Packet {

    public int a;
    public int b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet18ArmAnimation() {}

    public Packet18ArmAnimation(Entity entity, int i) {
        this.a = entity.id;
        this.b = i;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet18Data packetData = packetDataCodec.readPacket18(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getAnimation();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket18(
                new PacketDataCodec.Packet18Data(this.a, this.b),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet18Length();
    }
}
