package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet21PickupSpawn extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public byte e;
    public byte f;
    public byte g;
    public int h;
    public int i;
    public int l;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet21PickupSpawn() {}

    public Packet21PickupSpawn(EntityItem entityitem) {
        PacketDataCodec.Packet21Data packetData = packetDataCodec.packet21FromPickupEntity(entityitem);
        this.a = packetData.getEntityId();
        this.h = packetData.getItemId();
        this.i = packetData.getItemCount();
        this.l = packetData.getItemData();
        this.b = packetData.getX();
        this.c = packetData.getY();
        this.d = packetData.getZ();
        this.e = packetData.getVelocityX();
        this.f = packetData.getVelocityY();
        this.g = packetData.getVelocityZ();
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet21Data packetData = packetDataCodec.readPacket21(datainputstream);
        this.a = packetData.getEntityId();
        this.h = packetData.getItemId();
        this.i = packetData.getItemCount();
        this.l = packetData.getItemData();
        this.b = packetData.getX();
        this.c = packetData.getY();
        this.d = packetData.getZ();
        this.e = packetData.getVelocityX();
        this.f = packetData.getVelocityY();
        this.g = packetData.getVelocityZ();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket21(
                new PacketDataCodec.Packet21Data(this.a, this.h, this.i, this.l, this.b, this.c, this.d, this.e, this.f, this.g),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet21Length();
    }
}
