package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet105CraftProgressBar extends Packet {

    public int a;
    public int b;
    public int c;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet105CraftProgressBar() {}

    public Packet105CraftProgressBar(int i, int j, int k) {
        this.a = i;
        this.b = j;
        this.c = k;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet105Data packetData = packetDataCodec.readPacket105(datainputstream);
        this.a = packetData.getWindowId();
        this.b = packetData.getProperty();
        this.c = packetData.getValue();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket105(
                new PacketDataCodec.Packet105Data(this.a, this.b, this.c),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet105Length();
    }
}
