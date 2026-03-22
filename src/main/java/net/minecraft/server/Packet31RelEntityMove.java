package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet31RelEntityMove extends Packet30Entity {

    public Packet31RelEntityMove() {}

    public Packet31RelEntityMove(int i, byte b0, byte b1, byte b2) {
        super(i);
        this.b = b0;
        this.c = b1;
        this.d = b2;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet31Data packetData = PacketDataCodec.getInstance().readPacket31(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getDeltaX();
        this.c = packetData.getDeltaY();
        this.d = packetData.getDeltaZ();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.getInstance().writePacket31(
                new PacketDataCodec.Packet31Data(this.a, this.b, this.c, this.d),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet31Length();
    }
}
