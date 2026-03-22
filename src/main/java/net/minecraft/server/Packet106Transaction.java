package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet106Transaction extends Packet {

    public int a;
    public short b;
    public boolean c;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet106Transaction() {}

    public Packet106Transaction(int i, short short1, boolean flag) {
        this.a = i;
        this.b = short1;
        this.c = flag;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet106Data packetData = packetDataCodec.readPacket106(datainputstream);
        this.a = packetData.getWindowId();
        this.b = packetData.getActionNumber();
        this.c = packetData.isAccepted();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket106(
                new PacketDataCodec.Packet106Data(this.a, this.b, this.c),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet106Length();
    }
}
