package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet28EntityVelocity extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet28EntityVelocity() {}

    public Packet28EntityVelocity(Entity entity) {
        this(entity.id, entity.motX, entity.motY, entity.motZ);
    }

    public Packet28EntityVelocity(int i, double d0, double d1, double d2) {
        this.a = i;
        this.b = (int) (d0 * 8000.0D);
        this.c = (int) (d1 * 8000.0D);
        this.d = (int) (d2 * 8000.0D);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet28Data packetData = packetDataCodec.readPacket28(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getVelocityX();
        this.c = packetData.getVelocityY();
        this.d = packetData.getVelocityZ();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket28(
                new PacketDataCodec.Packet28Data(this.a, this.b, this.c, this.d),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet28Length();
    }
}
