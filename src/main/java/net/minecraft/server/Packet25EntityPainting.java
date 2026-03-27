package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet25EntityPainting extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet25EntityPainting() {}

    public Packet25EntityPainting(EntityPainting entitypainting) {
        this.a = entitypainting.id;
        this.f = entitypainting.e.A;
        this.b = entitypainting.b;
        this.c = entitypainting.c;
        this.d = entitypainting.d;
        this.e = entitypainting.a;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet25Data packetData = packetDataCodec.readPacket25(datainputstream);
        this.a = packetData.getEntityId();
        this.f = packetData.getArtName();
        this.b = packetData.getTileX();
        this.c = packetData.getTileY();
        this.d = packetData.getTileZ();
        this.e = packetData.getDirection();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket25(
                new PacketDataCodec.Packet25Data(this.a, this.f, this.b, this.c, this.d, this.e),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet25Length(this.f);
    }
}
