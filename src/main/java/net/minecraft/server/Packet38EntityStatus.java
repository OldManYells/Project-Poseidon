package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet38EntityStatus extends Packet {

    public int a;
    public byte b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet38EntityStatus() {}

    public Packet38EntityStatus(int i, byte b0) {
        this.a = i;
        this.b = b0;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet38Data packetData = packetDataCodec.readPacket38(datainputstream);
        this.a = packetData.getEntityId();
        this.b = packetData.getStatusByte();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket38(new PacketDataCodec.Packet38Data(this.a, this.b), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet38Length();
    }
}
