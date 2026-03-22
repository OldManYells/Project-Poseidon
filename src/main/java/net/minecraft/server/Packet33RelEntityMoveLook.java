package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet33RelEntityMoveLook extends Packet30Entity {

    public Packet33RelEntityMoveLook() {
        this.g = true;
    }

    public Packet33RelEntityMoveLook(int i, byte b0, byte b1, byte b2, byte b3, byte b4) {
        super(i);
        this.b = b0;
        this.c = b1;
        this.d = b2;
        this.e = b3;
        this.f = b4;
        this.g = true;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet33Data packetData = PacketDataCodec.getInstance().readPacket33(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getDeltaX();
        this.c = packetData.getDeltaY();
        this.d = packetData.getDeltaZ();
        this.e = packetData.getYaw();
        this.f = packetData.getPitch();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.getInstance().writePacket33(
                new PacketDataCodec.Packet33Data(this.a, this.b, this.c, this.d, this.e, this.f),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet33Length();
    }
}
