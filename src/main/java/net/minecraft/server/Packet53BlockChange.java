package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet53BlockChange extends Packet {

    public int a;
    public int b;
    public int c;
    public int material;
    public int data;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet53BlockChange() {
        this.k = true;
    }

    public Packet53BlockChange(int i, int j, int k, World world) {
        this.k = true;
        this.a = i;
        this.b = j;
        this.c = k;
        this.material = world.getTypeId(i, j, k);
        this.data = world.getData(i, j, k);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet53Data packetData = packetDataCodec.readPacket53(datainputstream);
        this.a = packetData.getX();
        this.b = packetData.getY();
        this.c = packetData.getZ();
        this.material = packetData.getMaterialId();
        this.data = packetData.getDataValue();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket53(
                new PacketDataCodec.Packet53Data(this.a, this.b, this.c, this.material, this.data),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet53Length();
    }
}
