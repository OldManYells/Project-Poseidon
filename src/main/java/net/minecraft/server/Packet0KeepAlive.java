package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet0KeepAlive extends Packet {
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet0KeepAlive() {
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        packetDataCodec.readPacket0(datainputstream);
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket0(dataoutputstream);
    }

    public int a() {
        return packetDataCodec.packet0Length();
    }
}
