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
        PacketDataCodec.Packet28Data packetData = packetDataCodec.packet28FromEntity(entity);
        this.a = packetData.getEntityId();
        this.b = packetData.getVelocityX();
        this.c = packetData.getVelocityY();
        this.d = packetData.getVelocityZ();
    }

    public Packet28EntityVelocity(int i, double d0, double d1, double d2) {
        PacketDataCodec.Packet28Data packetData = packetDataCodec.packet28FromMotion(i, d0, d1, d2);
        this.a = packetData.getEntityId();
        this.b = packetData.getVelocityX();
        this.c = packetData.getVelocityY();
        this.d = packetData.getVelocityZ();
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
