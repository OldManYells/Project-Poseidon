package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet {

    public int a;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet8UpdateHealth() {}

    public Packet8UpdateHealth(int i) {
        this.a = i;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = packetDataCodec.readPacket8(datainputstream).getHealth();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket8(new PacketDataCodec.Packet8Data(this.a), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet8Length();
    }
}
