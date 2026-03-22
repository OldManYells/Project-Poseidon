package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet23VehicleSpawn extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet23VehicleSpawn() {}

    public Packet23VehicleSpawn(Entity entity, int i) {
        this(entity, i, 0);
    }

    public Packet23VehicleSpawn(Entity entity, int i, int j) {
        PacketDataCodec.Packet23Data packetData = packetDataCodec.packet23FromVehicleEntity(entity, i, j);
        this.a = packetData.getEntityId();
        this.h = packetData.getType();
        this.b = packetData.getX();
        this.c = packetData.getY();
        this.d = packetData.getZ();
        this.i = packetData.getThrowerId();
        this.e = packetData.getVelocityX();
        this.f = packetData.getVelocityY();
        this.g = packetData.getVelocityZ();
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet23Data packetData = packetDataCodec.readPacket23(datainputstream);
        this.a = packetData.getEntityId();
        this.h = packetData.getType();
        this.b = packetData.getX();
        this.c = packetData.getY();
        this.d = packetData.getZ();
        this.i = packetData.getThrowerId();
        this.e = packetData.getVelocityX();
        this.f = packetData.getVelocityY();
        this.g = packetData.getVelocityZ();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket23(
                new PacketDataCodec.Packet23Data(this.a, this.h, this.b, this.c, this.d, this.i, this.e, this.f, this.g),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet23Length(this.i);
    }
}
