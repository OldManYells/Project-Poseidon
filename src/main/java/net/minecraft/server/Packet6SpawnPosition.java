package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet6SpawnPosition extends Packet {

    public int x;
    public int y;
    public int z;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet6SpawnPosition() {}

    public Packet6SpawnPosition(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet6Data packetData = packetDataCodec.readPacket6(datainputstream);
        this.x = packetData.getX();
        this.y = packetData.getY();
        this.z = packetData.getZ();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket6(new PacketDataCodec.Packet6Data(this.x, this.y, this.z), dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet6Length();
    }
}
