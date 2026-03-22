package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet29DestroyEntity extends Packet {

    public int a;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet29DestroyEntity() {}

    public Packet29DestroyEntity(int i) {
        this.a = i;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = packetDataCodec.readPacket29(datainputstream).getEntityId();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket29(new PacketDataCodec.Packet29Data(this.a), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet29Length();
    }
}
