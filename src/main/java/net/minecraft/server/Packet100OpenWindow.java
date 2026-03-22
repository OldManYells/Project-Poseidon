package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet100OpenWindow extends Packet {

    public int a;
    public int b;
    public String c;
    public int d;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet100OpenWindow() {}

    public Packet100OpenWindow(int i, int j, String s, int k) {
        this.a = i;
        this.b = j;
        this.c = s;
        this.d = k;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet100Data packetData = packetDataCodec.readPacket100(datainputstream);
        this.a = packetData.getWindowId();
        this.b = packetData.getWindowType();
        this.c = packetData.getTitle();
        this.d = packetData.getSlotCount();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket100(
                new PacketDataCodec.Packet100Data(this.a, this.b, this.c, this.d),
                dataoutputstream
        );
    }

    public int a() {
        return packetDataCodec.packet100Length(this.c);
    }
}
