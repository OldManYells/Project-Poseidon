package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet32EntityLook extends Packet30Entity {

    public Packet32EntityLook() {
        this.g = true;
    }

    public Packet32EntityLook(int i, byte b0, byte b1) {
        super(i);
        this.e = b0;
        this.f = b1;
        this.g = true;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet32Data packetData = PacketDataCodec.getInstance().readPacket32(datainputstream);
        this.a = packetData.getEntityId();
        this.e = packetData.getYaw();
        this.f = packetData.getPitch();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        PacketDataCodec.getInstance().writePacket32(
                new PacketDataCodec.Packet32Data(this.a, this.e, this.f),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet32Length();
    }
}
