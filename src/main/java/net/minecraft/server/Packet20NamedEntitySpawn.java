package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet20NamedEntitySpawn extends Packet {

    public int a;
    public String b;
    public int c;
    public int d;
    public int e;
    public byte f;
    public byte g;
    public int h;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet20NamedEntitySpawn() {}

    public Packet20NamedEntitySpawn(EntityHuman entityhuman) {
        ItemStack itemStack = entityhuman.inventory.getItemInHand();
        this.a = entityhuman.id;
        this.b = entityhuman.name;
        this.c = (int) (entityhuman.locX * 32.0D);
        this.d = (int) (entityhuman.locY * 32.0D);
        this.e = (int) (entityhuman.locZ * 32.0D);
        this.f = (byte) ((int) (entityhuman.yaw * 256.0F / 360.0F));
        this.g = (byte) ((int) (entityhuman.pitch * 256.0F / 360.0F));
        this.h = itemStack == null ? 0 : itemStack.id;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet20Data packetData = packetDataCodec.readPacket20(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getPlayerName();
        this.c = packetData.getX();
        this.d = packetData.getY();
        this.e = packetData.getZ();
        this.f = packetData.getYaw();
        this.g = packetData.getPitch();
        this.h = packetData.getHeldItemId();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket20(
                new PacketDataCodec.Packet20Data(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet20Length();
    }
}
