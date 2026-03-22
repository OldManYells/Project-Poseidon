package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet9Respawn extends Packet {

    public byte a;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet9Respawn() {}

    public Packet9Respawn(byte b0) {
        this.a = b0;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = packetDataCodec.readPacket9(datainputstream).getDimension();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket9(new PacketDataCodec.Packet9Data(this.a), dataoutputstream);
    }

    public int a() {
        return packetDataCodec.packet9Length();
    }
}
