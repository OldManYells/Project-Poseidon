package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet200Statistic extends Packet {

    public int a;
    public int b;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet200Statistic() {}

    public Packet200Statistic(int i, int j) {
        this.a = i;
        this.b = j;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet200Data packetData = packetDataCodec.readPacket200(datainputstream);
        this.a = packetData.getStatisticId();
        this.b = packetData.getAmount();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket200(new PacketDataCodec.Packet200Data(this.a, this.b), dataoutputstream);
    }

    public int a() {
        return packetDataCodec.packet200Length();
    }
}
