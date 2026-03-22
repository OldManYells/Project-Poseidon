package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet27 extends Packet {

    private float a;
    private float b;
    private boolean c;
    private boolean d;
    private float e;
    private float f;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet27() {}

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet27Data data = packetDataCodec.readPacket27(datainputstream);
        this.a = data.getPrimaryX();
        this.b = data.getPrimaryY();
        this.e = data.getSecondaryX();
        this.f = data.getSecondaryY();
        this.c = data.isPrimaryFlag();
        this.d = data.isSecondaryFlag();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket27(
                new PacketDataCodec.Packet27Data(this.a, this.b, this.e, this.f, this.c, this.d),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet27Length();
    }

    public float c() {
        return this.a;
    }

    public float d() {
        return this.e;
    }

    public float e() {
        return this.b;
    }

    public float f() {
        return this.f;
    }

    public boolean g() {
        return this.c;
    }

    public boolean h() {
        return this.d;
    }
}
