package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet101CloseWindow extends Packet {

    public int a;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet101CloseWindow() {}

    public Packet101CloseWindow(int i) {
        this.a = i;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = packetDataCodec.readPacket101(datainputstream).getWindowId();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket101(new PacketDataCodec.Packet101Data(this.a), dataoutputstream);
    }

    public int a() {
        return packetDataCodec.packet101Length();
    }
}
