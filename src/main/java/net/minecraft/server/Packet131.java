package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet131 extends Packet {

    public short a;
    public short b;
    public byte[] c;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet131() {
        this.k = true;
    }

    public Packet131(short short1, short short2, byte[] abyte) {
        this.k = true;
        this.a = short1;
        this.b = short2;
        this.c = abyte;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet131Data data = packetDataCodec.readPacket131(datainputstream);
        this.a = data.getItemId();
        this.b = data.getDamage();
        this.c = data.getPayload();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket131(
                new PacketDataCodec.Packet131Data(this.a, this.b, this.c),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet131Length(this.c);
    }
}
