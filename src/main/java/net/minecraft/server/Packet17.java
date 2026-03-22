package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet17 extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet17() {}

    public Packet17(Entity entity, int i, int j, int k, int l) {
        this.e = i;
        this.b = j;
        this.c = k;
        this.d = l;
        this.a = entity.id;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet17Data data = packetDataCodec.readPacket17(datainputstream);
        this.a = data.getEntityId();
        this.e = data.getType();
        this.b = data.getPrimaryValue();
        this.c = data.getAuxiliaryByteValue();
        this.d = data.getSecondaryValue();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket17(
                new PacketDataCodec.Packet17Data(this.a, this.e, this.b, this.c, this.d),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet17Length();
    }
}
