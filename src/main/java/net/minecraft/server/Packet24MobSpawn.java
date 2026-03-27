package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet24MobSpawn extends Packet {

    public int a;
    public byte b;
    public int c;
    public int d;
    public int e;
    public byte f;
    public byte g;
    private DataWatcher h;
    private List i;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet24MobSpawn() {}

    public Packet24MobSpawn(EntityLiving entityliving) {
        this.a = entityliving.id;
        this.b = (byte) EntityTypes.a(entityliving);
        this.c = (int) (entityliving.locX * 32.0D);
        this.d = (int) (entityliving.locY * 32.0D);
        this.e = (int) (entityliving.locZ * 32.0D);
        this.f = (byte) ((int) (entityliving.yaw * 256.0F / 360.0F));
        this.g = (byte) ((int) (entityliving.pitch * 256.0F / 360.0F));
        this.h = entityliving.aa();
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet24Data packetData = packetDataCodec.readPacket24(datainputstream);
        this.a = packetData.getEntityId();
        this.b = (byte) packetData.getEntityType();
        this.c = packetData.getX();
        this.d = packetData.getY();
        this.e = packetData.getZ();
        this.f = packetData.getYaw();
        this.g = packetData.getPitch();
        this.i = packetData.getMetadata();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket24(
                new PacketDataCodec.Packet24Data(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.i),
                this.h,
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet24Length();
    }
}
