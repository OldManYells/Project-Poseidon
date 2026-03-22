package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet54PlayNoteBlock extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet54PlayNoteBlock() {}

    public Packet54PlayNoteBlock(int i, int j, int k, int l, int i1) {
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = l;
        this.e = i1;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet54Data packetData = packetDataCodec.readPacket54(datainputstream);
        this.a = packetData.getX();
        this.b = packetData.getY();
        this.c = packetData.getZ();
        this.d = packetData.getInstrument();
        this.e = packetData.getPitch();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket54(
                new PacketDataCodec.Packet54Data(this.a, this.b, this.c, this.d, this.e),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet54Length();
    }
}
