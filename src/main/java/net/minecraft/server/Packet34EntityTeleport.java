package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet34EntityTeleport extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public byte e;
    public byte f;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet34EntityTeleport() {}

    public Packet34EntityTeleport(Entity entity) {
        PacketDataCodec.Packet34Data packetData = packetDataCodec.packet34FromEntity(entity);
        this.a = packetData.getEntityId();
        this.b = packetData.getX();
        this.c = packetData.getY();
        this.d = packetData.getZ();
        this.e = packetData.getYaw();
        this.f = packetData.getPitch();
    }

    public Packet34EntityTeleport(int i, int j, int k, int l, byte b0, byte b1) {
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = l;
        this.e = b0;
        this.f = b1;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet34Data packetData = packetDataCodec.readPacket34(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getX();
        this.c = packetData.getY();
        this.d = packetData.getZ();
        this.e = packetData.getYaw();
        this.f = packetData.getPitch();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket34(
                new PacketDataCodec.Packet34Data(this.a, this.b, this.c, this.d, this.e, this.f),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet34Length();
    }
}
